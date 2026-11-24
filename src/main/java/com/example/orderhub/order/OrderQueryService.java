package com.example.orderhub.order;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
@Service
public class OrderQueryService {
    private static final int DEFAULT_SIZE=20,MAX_SIZE=100;
    private final OrderRepository repository;
    public OrderQueryService(OrderRepository repository) { this.repository=repository; }
    public List<OrderEntity> findByDateRange(LocalDate start,LocalDate end) {
        if(start==null || end==null || end.isBefore(start)) throw new IllegalArgumentException("valid start and end dates are required");
        return repository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(start.atStartOfDay(),end.atStartOfDay());
    }
    public Page<OrderEntity> findPage(Integer page,Integer size) {
        int p=page==null?0:page, s=size==null?DEFAULT_SIZE:size;
        // Pass the requested paging values to the repository.
        return repository.findAllWithUser(PageRequest.of(p,s));
    }
}