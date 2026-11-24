package com.example.orderhub.order;
import com.example.orderhub.inventory.*;
import com.example.orderhub.message.*;
import com.example.orderhub.user.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class OrderFlowConsistencyTest {
    @Autowired private InventoryService inventoryService;
    @Autowired private InventoryRepository inventoryRepository;
    @Autowired private OrderEventConsumer eventConsumer;
    @Autowired private OrderEventLogRepository eventLogRepository;
    @Autowired private OrderApplicationService applicationService;
    @Autowired private UserRepository userRepository;
    @Autowired private OrderRepository orderRepository;

    @Test void concurrentDeductDoesNotLoseUpdates() throws Exception {
        Long productId=91001L;
        int threads=5;
        int perThread=2;
        inventoryRepository.saveAndFlush(new InventoryItem(productId,100));
        ExecutorService pool=Executors.newFixedThreadPool(threads);
        CountDownLatch ready=new CountDownLatch(threads);
        CountDownLatch start=new CountDownLatch(1);
        AtomicInteger failures=new AtomicInteger();
        List<Future<?>> futures=new ArrayList<>();
        for(int i=0;i<threads;i++) {
            futures.add(pool.submit(() -> {
                ready.countDown();
                try {
                    start.await();
                    inventoryService.deduct(productId,perThread);
                } catch(Exception e) {
                    failures.incrementAndGet();
                }
            }));
        }
        assertTrue(ready.await(5,TimeUnit.SECONDS));
        start.countDown();
        for(Future<?> future:futures) future.get(30,TimeUnit.SECONDS);
        pool.shutdown();
        assertEquals(0,failures.get());
        assertEquals(100-threads*perThread,
                inventoryRepository.findByProductId(productId).orElseThrow().getQuantity());
    }

    @Test void duplicateEventIsConsumedOnlyOnce() {
        String eventId="evt-" + UUID.randomUUID();
        assertTrue(eventConsumer.consume(eventId));
        assertFalse(eventConsumer.consume(eventId));
        assertEquals(1,eventLogRepository.countByEventId(eventId));
    }

    @Test void orderCreationRollsBackWhenInventoryIsInsufficient() {
        UserEntity user=userRepository.save(new UserEntity("ConsistencyTestUser"));
        Long productId=91002L;
        inventoryRepository.saveAndFlush(new InventoryItem(productId,1));
        long ordersBefore=orderRepository.count();
        assertThrows(IllegalStateException.class,
                () -> applicationService.createOrder(user.getId(),productId,2,new BigDecimal("100.00"),BigDecimal.ZERO));
        assertEquals(ordersBefore,orderRepository.count());
        assertEquals(1,inventoryRepository.findByProductId(productId).orElseThrow().getQuantity());
    }
}
