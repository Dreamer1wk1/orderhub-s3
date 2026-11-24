package com.example.orderhub.order;
import com.example.orderhub.user.UserEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity @Table(name="orders")
public class OrderEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="user_id",nullable=false) private UserEntity user;
    @Column(nullable=false,length=30) private String status;
    @Column(nullable=false,precision=12,scale=2) private BigDecimal totalAmount;
    @Column(nullable=false) private LocalDateTime createdAt;
    protected OrderEntity() {}
    public OrderEntity(UserEntity user,String status,BigDecimal totalAmount,LocalDateTime createdAt) {
        this.user=user; this.status=status; this.totalAmount=totalAmount; this.createdAt=createdAt;
    }
    public Long getId() { return id; }
    public UserEntity getUser() { return user; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status=status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}