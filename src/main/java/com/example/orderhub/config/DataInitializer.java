package com.example.orderhub.config;
import com.example.orderhub.inventory.*;
import com.example.orderhub.product.*;
import com.example.orderhub.user.*;
import org.springframework.boot.*;
import org.springframework.stereotype.Component;
@Component
public class DataInitializer implements ApplicationRunner {
    private final UserRepository users; private final ProductRepository products; private final InventoryRepository inventory;
    public DataInitializer(UserRepository users,ProductRepository products,InventoryRepository inventory) {
        this.users=users; this.products=products; this.inventory=inventory;
    }
    @Override public void run(ApplicationArguments args) {
        if(users.count()==0) { users.save(new UserEntity("Alice")); users.save(new UserEntity("Bob")); }
        if(products.count()==0) {
            products.save(new ProductEntity("Spring Boot实战课程","BOOK"));
            products.save(new ProductEntity("无线键盘","ELECTRONICS"));
            products.save(new ProductEntity("办公背包","LIFESTYLE"));
        }
        if(inventory.count()==0) {
            inventory.save(new InventoryItem(1L,100)); inventory.save(new InventoryItem(2L,50)); inventory.save(new InventoryItem(3L,30));
        }
    }
}