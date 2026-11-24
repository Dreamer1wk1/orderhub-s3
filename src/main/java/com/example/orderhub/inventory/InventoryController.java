package com.example.orderhub.inventory;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService service; private final InventoryRepository repository;
    public InventoryController(InventoryService service,InventoryRepository repository) { this.service=service; this.repository=repository; }
    @PostMapping("/{productId}/deduct")
    public InventoryItem deduct(@PathVariable Long productId,@RequestParam Integer quantity) {
        service.deduct(productId,quantity);
        return repository.findByProductId(productId).orElseThrow(()->new IllegalArgumentException("inventory not found"));
    }
}