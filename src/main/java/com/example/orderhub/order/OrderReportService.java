package com.example.orderhub.order;
import com.example.orderhub.order.dto.OrderReportItem;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class OrderReportService {
    private final OrderRepository repository;
    public OrderReportService(OrderRepository repository) { this.repository=repository; }
    public List<OrderReportItem> latestOrders(int page,int size) {
        return repository.findAll(PageRequest.of(page,size)).getContent().stream()
                .map(o->new OrderReportItem(o.getId(),o.getStatus(),o.getTotalAmount())).toList();
    }
}