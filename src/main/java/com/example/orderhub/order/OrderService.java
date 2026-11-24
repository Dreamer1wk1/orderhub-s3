package com.example.orderhub.order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
@Service
public class OrderService {
    private final OrderRepository repository;
    public OrderService(OrderRepository repository) { this.repository=repository; }
    @Transactional(readOnly=true)
    public OrderEntity getOrderForUser(Long orderId,Long userId) {
        OrderEntity order=repository.findById(orderId).orElseThrow(()->new NoSuchElementException("order not found"));
        if(!order.getUser().getId().equals(userId)) throw new SecurityException("you cannot access this order");
        return order;
    }
    public OrderEntity cancel(Long orderId) {
        OrderEntity order=repository.findById(orderId).orElseThrow(()->new NoSuchElementException("order not found"));
        if(!"CREATED".equals(order.getStatus())) throw new IllegalStateException("only CREATED orders can be cancelled");
        order.setStatus("CANCELLED");
        return repository.save(order);
    }
}