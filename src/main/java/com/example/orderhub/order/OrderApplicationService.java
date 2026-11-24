package com.example.orderhub.order;
import com.example.orderhub.inventory.InventoryService;
import com.example.orderhub.user.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Service
public class OrderApplicationService {
    private final UserRepository users;
    private final OrderRepository orders;
    private final OrderPricingService pricing;
    private final InventoryService inventory;
    public OrderApplicationService(UserRepository users,OrderRepository orders,OrderPricingService pricing,InventoryService inventory) {
        this.users=users; this.orders=orders; this.pricing=pricing; this.inventory=inventory;
    }
    public OrderEntity createOrder(Long userId,Long productId,Integer quantity,BigDecimal originalAmount,BigDecimal discountRate) {
        UserEntity user=users.findById(userId).orElseThrow(()->new IllegalArgumentException("user not found"));
        BigDecimal discounted=pricing.calculateDiscountedAmount(originalAmount,discountRate);
        BigDecimal shipping=pricing.shippingFee(discounted);
        OrderEntity order=orders.save(new OrderEntity(user,"CREATED",discounted.add(shipping),LocalDateTime.now()));
        inventory.deduct(productId,quantity);
        return order;
    }
}
