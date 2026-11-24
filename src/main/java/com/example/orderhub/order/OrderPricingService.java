package com.example.orderhub.order;
import org.springframework.stereotype.Service;
import java.math.*;
@Service
public class OrderPricingService {
    private static final BigDecimal FREE_SHIPPING_THRESHOLD=new BigDecimal("99.00");
    private static final BigDecimal SHIPPING_FEE=new BigDecimal("10.00");
    public BigDecimal calculateDiscountedAmount(BigDecimal originalAmount,BigDecimal discountRate) {
        if(originalAmount==null || originalAmount.signum()<0) throw new IllegalArgumentException("originalAmount must be non-negative");
        if(discountRate==null || discountRate.compareTo(BigDecimal.ZERO)<0 || discountRate.compareTo(BigDecimal.ONE)>0)
            throw new IllegalArgumentException("discountRate must be between 0 and 1");
        return originalAmount.multiply(BigDecimal.ONE.subtract(discountRate)).setScale(2,RoundingMode.HALF_UP);
    }
    public BigDecimal shippingFee(BigDecimal discountedAmount) {
        return discountedAmount.compareTo(FREE_SHIPPING_THRESHOLD)>=0?BigDecimal.ZERO:SHIPPING_FEE;
    }
}