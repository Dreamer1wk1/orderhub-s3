package com.example.orderhub.order;
import com.example.orderhub.order.dto.OrderSummary;
import com.example.orderhub.user.UserEntity;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
class OrderListServiceTest {
    @Test void loadsOrdersAndUsersWithJoinFetch() {
        OrderRepository repository=mock(OrderRepository.class);
        OrderEntity order=new OrderEntity(new UserEntity("Alice"),"CREATED",new BigDecimal("99.00"),LocalDateTime.of(2026,1,1,0,0));
        when(repository.findAllWithUser()).thenReturn(List.of(order));
        List<OrderSummary> result=new OrderListService(repository).listAll();
        assertEquals(1,result.size());
        assertEquals("Alice",result.get(0).customerName());
        verify(repository).findAllWithUser();
        verify(repository,never()).findAll();
    }
}