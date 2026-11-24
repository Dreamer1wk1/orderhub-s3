package com.example.orderhub.order;
import com.example.orderhub.inventory.InventoryService;
import com.example.orderhub.user.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
class OrderApplicationServiceTest {
    private final UserRepository users=mock(UserRepository.class);
    private final OrderRepository orders=mock(OrderRepository.class);
    private final InventoryService inventory=mock(InventoryService.class);
    private final OrderApplicationService service=new OrderApplicationService(users,orders,new OrderPricingService(),inventory);
    @Test void chargesShippingWhenDiscountedAmountBelowThreshold() {
        when(users.findById(1L)).thenReturn(Optional.of(new UserEntity("Alice")));
        when(orders.save(any())).thenAnswer(invocation->invocation.getArgument(0));
        OrderEntity order=service.createOrder(1L,10L,1,new BigDecimal("100.00"),new BigDecimal("0.50"));
        assertEquals(new BigDecimal("60.00"),order.getTotalAmount());
        verify(inventory).deduct(10L,1);
    }
}