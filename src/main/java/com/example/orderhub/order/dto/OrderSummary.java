package com.example.orderhub.order.dto;
import java.math.BigDecimal;
public record OrderSummary(Long orderId,String customerName,String status,BigDecimal totalAmount) {}