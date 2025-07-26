package com.example.myfurniture.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private LocalDate orderDate;
    private double price;
    private int quantity;
    private String status;
    String paymentMethod;
    String note;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany
    private Set<OrderItem> items;

}
