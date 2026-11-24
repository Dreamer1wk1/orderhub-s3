package com.example.orderhub.report;
import com.example.orderhub.order.OrderReportService;
import com.example.orderhub.order.dto.OrderReportItem;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final OrderReportService orders; private final DateFormatService dates;
    public ReportController(OrderReportService orders,DateFormatService dates) { this.orders=orders; this.dates=dates; }
    @GetMapping("/orders")
    public List<OrderReportItem> orders(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size) {
        return orders.latestOrders(page,size);
    }
    @GetMapping("/generated-at")
    public Map<String,String> generatedAt() { return Map.of("generatedAt",dates.format(Instant.now())); }
}