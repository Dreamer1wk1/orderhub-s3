package com.example.orderhub.product;
import jakarta.persistence.*;
@Entity @Table(name="products")
public class ProductEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=160) private String name;
    @Column(length=80) private String tag;
    protected ProductEntity() {}
    public ProductEntity(String name,String tag) { this.name=name; this.tag=tag; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getTag() { return tag; }
}