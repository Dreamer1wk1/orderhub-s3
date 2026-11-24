package com.example.orderhub.order;
import com.example.orderhub.user.UserEntity;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class OrderServiceTest {
    private final OrderRepository repository=mock(OrderRepository.class);
    private final OrderService service=new OrderService(repository);
    @Test void cancelsCreatedOrder() {
        OrderEntity order=new OrderEntity(new UserEntity("Alice"),"CREATED",new BigDecimal("10.00"),LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(order));
        when(repository.save(order)).thenReturn(order);
        assertEquals("CANCELLED",service.cancel(1L).getStatus());
    }
    @Test void rejectsCancellingNonCreatedOrder() {
        OrderEntity order=new OrderEntity(new UserEntity("Alice"),"SHIPPED",new BigDecimal("10.00"),LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(order));
        assertThrows(IllegalStateException.class,()->service.cancel(1L));
        assertEquals("SHIPPED",order.getStatus());
    }
}