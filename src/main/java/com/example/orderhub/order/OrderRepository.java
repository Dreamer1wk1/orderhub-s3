package com.example.orderhub.order;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import java.time.LocalDateTime;
import java.util.List;
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    @Query("select o from OrderEntity o join fetch o.user")
    List<OrderEntity> findAllWithUser();
    @Query(value="select o from OrderEntity o join fetch o.user",countQuery="select count(o) from OrderEntity o")
    Page<OrderEntity> findAllWithUser(Pageable pageable);
    List<OrderEntity> findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(LocalDateTime start,LocalDateTime end);
}