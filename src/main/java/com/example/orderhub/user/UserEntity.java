package com.example.orderhub.user;
import jakarta.persistence.*;
@Entity @Table(name="users")
public class UserEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=100) private String name;
    protected UserEntity() {}
    public UserEntity(String name) { this.name=name; }
    public Long getId() { return id; }
    public String getName() { return name; }
}