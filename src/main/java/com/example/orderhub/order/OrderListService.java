package com.example.orderhub.order;
import com.example.orderhub.order.dto.OrderSummary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class OrderListService {
    private final OrderRepository repository;
    public OrderListService(OrderRepository repository) { this.repository=repository; }
    @Transactional(readOnly=true)
    public List<OrderSummary> listAll() {
        return repository.findAll().stream().map(o->new OrderSummary(o.getId(),o.getUser().getName(),o.getStatus(),o.getTotalAmount())).toList();
    }
}
