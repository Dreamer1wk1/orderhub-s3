package com.example.orderhub.security;
import com.example.orderhub.order.*;
import com.example.orderhub.product.*;
import com.example.orderhub.user.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class SecurityRegressionTest {
    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProductSearchRepository productSearchRepository;
    @Test
    void rejectsAccessToAnotherUsersOrder() {
        UserEntity owner=userRepository.save(new UserEntity("Owner"));
        UserEntity other=userRepository.save(new UserEntity("Other"));
        OrderEntity order=orderRepository.save(new OrderEntity(owner,"CREATED",new BigDecimal("10.00"),LocalDateTime.now()));
        assertThrows(SecurityException.class,()->orderService.getOrderForUser(order.getId(),other.getId()));
        assertEquals(order.getId(),orderService.getOrderForUser(order.getId(),owner.getId()).getId());
    }
    @Test
    void treatsSqlInjectionPayloadAsLiteralKeyword() {
        List<ProductEntity> found=productSearchRepository.searchByName("' OR '1'='1");
        assertTrue(found.isEmpty(),"injection payload must not be interpreted as SQL");
    }
    @Test
    void stillSupportsNormalKeyword() {
        assertFalse(productSearchRepository.searchByName("键盘").isEmpty());
    }
}