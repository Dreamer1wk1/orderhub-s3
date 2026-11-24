package com.example.orderhub.report;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.assertEquals;
class DateFormatServiceTest {
    @Test void formatsInstantInShanghaiTimeZone() {
        assertEquals("2026-01-01 08:00:00",new DateFormatService().format(Instant.parse("2026-01-01T00:00:00Z")));
    }
}