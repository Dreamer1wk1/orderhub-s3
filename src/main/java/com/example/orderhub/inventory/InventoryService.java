package com.example.orderhub.inventory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class InventoryService {
    private final InventoryRepository repository;
    public InventoryService(InventoryRepository repository) { this.repository=repository; }
    @Transactional
    public void deduct(Long productId,Integer quantity) {
        if(productId==null || quantity==null || quantity<=0) throw new IllegalArgumentException("positive productId and quantity are required");
        int updated=repository.deductIfAvailable(productId,quantity);
        if(updated==0) throw new IllegalStateException("insufficient inventory or product not found");
    }
}
