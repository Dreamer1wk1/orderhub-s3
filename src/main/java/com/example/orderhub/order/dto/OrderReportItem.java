package com.example.orderhub.order.dto;
import java.math.BigDecimal;
public record OrderReportItem(Long orderId,String status,BigDecimal totalAmount) {}