package com.example.orderhub.order;
import com.example.orderhub.order.dto.OrderSummary;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderApplicationService applicationService;
    private final OrderService orderService;
    private final OrderQueryService queryService;
    private final OrderListService listService;
    public OrderController(OrderApplicationService applicationService,OrderService orderService,
                           OrderQueryService queryService,OrderListService listService) {
        this.applicationService=applicationService; this.orderService=orderService;
        this.queryService=queryService; this.listService=listService;
    }
    @PostMapping
    public Map<String,Object> create(@RequestBody CreateOrderRequest request) {
        OrderEntity order=applicationService.createOrder(request.userId(),request.productId(),request.quantity(),
                request.originalAmount(),request.discountRate());
        return Map.of("orderId",order.getId(),"totalAmount",order.getTotalAmount());
    }
    @GetMapping("/{orderId}")
    public OrderSummary get(@PathVariable Long orderId,@RequestHeader("X-User-Id") Long userId) {
        OrderEntity o=orderService.getOrderForUser(orderId,userId);
        return new OrderSummary(o.getId(),o.getUser().getName(),o.getStatus(),o.getTotalAmount());
    }
    @PostMapping("/{orderId}/cancel")
    public Map<String,Object> cancel(@PathVariable Long orderId) {
        OrderEntity o=orderService.cancel(orderId);
        return Map.of("orderId",o.getId(),"status",o.getStatus());
    }
    @GetMapping
    public List<OrderSummary> list() { return listService.listAll(); }
    @GetMapping("/by-date")
    public List<Map<String,Object>> byDate(
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate end) {
        return queryService.findByDateRange(start,end).stream()
                .map(o->Map.<String,Object>of("orderId",o.getId(),"status",o.getStatus(),"totalAmount",o.getTotalAmount()))
                .toList();
    }
    @GetMapping("/page")
    public Map<String,Object> page(@RequestParam(required=false) Integer page,@RequestParam(required=false) Integer size) {
        var result=queryService.findPage(page,size);
        return Map.of("content",result.getContent().stream()
                .map(o->new OrderSummary(o.getId(),o.getUser().getName(),o.getStatus(),o.getTotalAmount())).toList(),
                "totalElements",result.getTotalElements());
    }
    public record CreateOrderRequest(Long userId,Long productId,Integer quantity,BigDecimal originalAmount,BigDecimal discountRate) {}
}