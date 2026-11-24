package com.example.orderhub.message;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="order_event_logs")
public class OrderEventLog {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=120) private String eventId;
    @Column(nullable=false) private LocalDateTime processedAt;
    protected OrderEventLog() {}
    public OrderEventLog(String eventId,LocalDateTime processedAt) { this.eventId=eventId; this.processedAt=processedAt; }
    public Long getId() { return id; }
    public String getEventId() { return eventId; }
}