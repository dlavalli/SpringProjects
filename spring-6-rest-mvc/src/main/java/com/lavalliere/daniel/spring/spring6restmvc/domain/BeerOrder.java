package com.lavalliere.daniel.spring.spring6restmvc.domain;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
// @AllArgsConstructor
@Builder
public class BeerOrder {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false )
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    public BeerOrder(
        UUID id,
        Long version,
        Timestamp createdDate,
        Timestamp lastModifiedDate,
        String customerRef,
        Customer customer,
        Set<BeerOrderLine> beerOrderLines,
        BeerOrderShipment beerOrderShipment
    ) {
        this.id = id;
        this.version = version;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.customerRef = customerRef;
        this.setCustomer(customer);
        this.beerOrderLines = beerOrderLines;
        this.setBeerOrderShipment(beerOrderShipment);
    }

    public boolean isNew() {
        return this.id == null;
    }

    private String customerRef;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.getBeerOrders().add(this);
    }

    public void setBeerOrderShipment(BeerOrderShipment beerOrderShipment) {
        this.beerOrderShipment = beerOrderShipment;
        beerOrderShipment.setBeerOrder(this);
    }


    @ManyToOne
    private Customer customer;

    @Builder.Default
    @OneToMany(mappedBy = "beerOrder")
    private Set<BeerOrderLine> beerOrderLines = new HashSet<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    private BeerOrderShipment beerOrderShipment;
}
