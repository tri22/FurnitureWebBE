package com.example.myfurniture.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;
    private Double rating;
    @Builder.Default
    boolean deleted = false;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
