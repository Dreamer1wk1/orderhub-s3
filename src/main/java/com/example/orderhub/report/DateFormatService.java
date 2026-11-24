package com.example.orderhub.report;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
@Service
public class DateFormatService {
    private static final SimpleDateFormat FORMATTER=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String format(Instant instant) { return FORMATTER.format(Date.from(instant)); }
}
