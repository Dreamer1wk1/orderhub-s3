package com.example.orderhub.product;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class ProductSearchRepository {
    @PersistenceContext private EntityManager entityManager;
    public List<ProductEntity> searchByName(String keyword) {
        String sql="select * from products where name like '%"+keyword+"%'";
        return entityManager.createNativeQuery(sql,ProductEntity.class).getResultList();
    }
}
