package com.example.orderhub.order;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
class OrderPricingServiceTest {
    private final OrderPricingService service=new OrderPricingService();
    @Test void calculatesDiscountedAmount() {
        assertEquals(new BigDecimal("90.00"),service.calculateDiscountedAmount(new BigDecimal("100.00"),new BigDecimal("0.10")));
    }
    @Test void rejectsDiscountAboveOne() {
        assertThrows(IllegalArgumentException.class,()->service.calculateDiscountedAmount(new BigDecimal("100.00"),new BigDecimal("1.20")));
    }
    @Test void rejectsNegativeDiscount() {
        assertThrows(IllegalArgumentException.class,()->service.calculateDiscountedAmount(new BigDecimal("100.00"),new BigDecimal("-0.10")));
    }
}