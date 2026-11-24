package com.example.orderhub.order;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderQueryServiceTest {
    private final OrderRepository repository = mock(OrderRepository.class);
    private final OrderQueryService service = new OrderQueryService(repository);

    @Test
    void dateRangeCoversWholeEndDay() {
        when(repository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(any(), any())).thenReturn(List.of());
        service.findByDateRange(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 31));
        ArgumentCaptor<LocalDateTime> end = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(repository).findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(any(), end.capture());
        assertEquals(LocalDateTime.of(2026, 2, 1, 0, 0), end.getValue());
    }

    @Test
    void singleDayRangeQueriesThatWholeDay() {
        when(repository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(any(), any())).thenReturn(List.of());
        service.findByDateRange(LocalDate.of(2026, 1, 31), LocalDate.of(2026, 1, 31));
        ArgumentCaptor<LocalDateTime> start = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<LocalDateTime> end = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(repository).findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(start.capture(), end.capture());
        assertEquals(LocalDateTime.of(2026, 1, 31, 0, 0), start.getValue());
        assertEquals(LocalDateTime.of(2026, 2, 1, 0, 0), end.getValue());
    }

    @Test
    void rejectsOutOfRangePaging() {
        assertThrows(IllegalArgumentException.class, () -> service.findPage(-1, 20));
        assertThrows(IllegalArgumentException.class, () -> service.findPage(0, 0));
        assertThrows(IllegalArgumentException.class, () -> service.findPage(0, 101));
    }

    @Test
    void appliesDefaultPagingWhenNull() {
        when(repository.findAllWithUser(any(Pageable.class))).thenReturn(Page.empty());
        service.findPage(null, null);
        ArgumentCaptor<Pageable> pageable = ArgumentCaptor.forClass(Pageable.class);
        verify(repository).findAllWithUser(pageable.capture());
        assertEquals(0, pageable.getValue().getPageNumber());
        assertEquals(20, pageable.getValue().getPageSize());
    }
}