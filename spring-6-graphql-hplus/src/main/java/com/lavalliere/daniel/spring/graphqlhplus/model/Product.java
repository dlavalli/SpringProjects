package com.lavalliere.daniel.spring.graphqlhplus.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="PRODUCTS")
public class Product {
    @Id
    @Column(name="PRODUCT_ID")
    private String id;
    @Column(name="NAME")
    private String name;
    @Column(name="SIZE")
    private int size;
    @Column(name="VARIETY")
    private String variety;
    @Column(name="PRICE")
    private BigDecimal price;
    @Column(name="STATUS")
    private String status;

}
