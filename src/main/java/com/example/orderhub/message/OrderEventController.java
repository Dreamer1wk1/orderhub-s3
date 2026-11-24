package com.example.orderhub.message;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/order-events")
public class OrderEventController {
    private final OrderEventConsumer consumer; private final OrderEventLogRepository repository;
    public OrderEventController(OrderEventConsumer consumer,OrderEventLogRepository repository) { this.consumer=consumer; this.repository=repository; }
    @PostMapping
    public Map<String,Object> consume(@RequestBody EventRequest request) {
        boolean processed=consumer.consume(request.eventId());
        return Map.of("processed",processed,"eventCount",repository.countByEventId(request.eventId()));
    }
    public record EventRequest(String eventId) {}
}