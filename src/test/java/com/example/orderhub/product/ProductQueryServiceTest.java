package com.example.orderhub.product;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductQueryServiceTest {
    private final ProductSearchRepository repository = mock(ProductSearchRepository.class);
    private final ProductQueryService service = new ProductQueryService(repository);

    @Test
    void missingTagsReturnsAllMatches() {
        List<ProductEntity> products = List.of(new ProductEntity("book", "BOOK"));
        when(repository.searchByName("book")).thenReturn(products);
        assertEquals(products, service.search("book", null));
    }

    @Test
    void filtersByProvidedTags() {
        ProductEntity book = new ProductEntity("book", "BOOK");
        ProductEntity pen = new ProductEntity("pen", "STATIONERY");
        when(repository.searchByName("")).thenReturn(List.of(book, pen));
        assertEquals(List.of(book), service.search(null, List.of("BOOK")));
    }
}