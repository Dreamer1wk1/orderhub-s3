package com.example.orderhub.message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
@Service
public class OrderEventConsumer {
    private final OrderEventLogRepository repository;
    public OrderEventConsumer(OrderEventLogRepository repository) { this.repository=repository; }
    @Transactional
    public boolean consume(String eventId) {
        if(repository.existsByEventId(eventId)) return false;
        repository.save(new OrderEventLog(eventId,LocalDateTime.now()));
        return true;
    }
}