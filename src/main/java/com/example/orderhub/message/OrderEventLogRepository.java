package com.example.orderhub.message;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderEventLogRepository extends JpaRepository<OrderEventLog,Long> {
    boolean existsByEventId(String eventId);
    long countByEventId(String eventId);
}