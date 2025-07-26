package com.example.myfurniture.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Table(name = "stock_ins")
public class StockIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate createAt;
    private int quantity;
    private double price;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
