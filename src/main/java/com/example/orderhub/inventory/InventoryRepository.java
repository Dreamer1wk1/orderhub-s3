package com.example.orderhub.inventory;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
public interface InventoryRepository extends JpaRepository<InventoryItem,Long> {
    Optional<InventoryItem> findByProductId(Long productId);
    @Modifying(clearAutomatically=true)
    @Query("update InventoryItem i set i.quantity=i.quantity-:quantity where i.productId=:productId and i.quantity>=:quantity")
    int deductIfAvailable(@Param("productId") Long productId,@Param("quantity") Integer quantity);
}