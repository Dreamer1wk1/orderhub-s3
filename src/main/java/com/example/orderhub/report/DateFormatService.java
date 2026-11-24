package com.example.orderhub.report;
import org.springframework.stereotype.Service;
import java.time.*;
import java.time.format.DateTimeFormatter;
@Service
public class DateFormatService {
    private static final DateTimeFormatter FORMATTER=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"));
    public String format(Instant instant) { return FORMATTER.format(instant); }
}