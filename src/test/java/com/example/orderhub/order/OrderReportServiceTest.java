package com.example.orderhub.order;
import com.example.orderhub.user.UserEntity;
import com.example.orderhub.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OrderReportServiceTest {
    @Autowired private OrderReportService service;
    @Autowired private OrderRepository orders;
    @Autowired private UserRepository users;
    @Test void appliesRequestedPageSize() {
        UserEntity user=users.save(new UserEntity("ReportUser"));
        for(int i=0;i<5;i++) orders.save(new OrderEntity(user,"CREATED",new BigDecimal("10.00"),LocalDateTime.now()));
        assertEquals(2,service.latestOrders(0,2).size());
    }
}