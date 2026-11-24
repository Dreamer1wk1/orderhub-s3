package com.example.orderhub.product;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class ProductSearchRepository {
    @PersistenceContext private EntityManager entityManager;
    public List<ProductEntity> searchByName(String keyword) {
        return entityManager.createNativeQuery("select * from products where name like :keyword", ProductEntity.class)
                .setParameter("keyword","%"+keyword+"%").getResultList();
    }
}
