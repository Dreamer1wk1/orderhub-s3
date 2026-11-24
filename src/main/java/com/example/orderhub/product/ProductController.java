package com.example.orderhub.product;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductQueryService service;
    public ProductController(ProductQueryService service) { this.service=service; }
    @GetMapping("/search")
    public List<ProductEntity> search(@RequestParam(required=false) String keyword,
                                      @RequestParam(required=false) List<String> tags) {
        return service.search(keyword,tags);
    }
}