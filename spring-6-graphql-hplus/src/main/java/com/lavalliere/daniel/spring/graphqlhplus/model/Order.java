package com.lavalliere.daniel.spring.graphqlhplus.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ORDERS")
public class Order {
    @Id
    @Column(name="ORDER_ID")
    private String id;
    @ManyToOne
    @JoinColumn(name="CUSTOMER_ID", nullable = false, updatable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name="SALESPERSON_ID", nullable = false, updatable = false)
    private Salesperson salesperson;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;
}
