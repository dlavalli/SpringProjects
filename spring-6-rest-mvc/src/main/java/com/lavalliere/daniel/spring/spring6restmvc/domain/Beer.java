package com.lavalliere.daniel.spring.spring6restmvc.domain;

import com.lavalliere.daniel.spring.spring6restmvc.model.BeerStyle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Beer {

    @Id
    @Column(length=36, columnDefinition = "varchar", updatable = false, nullable = false)  // Hints to sql creation
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // For long id type
    @GeneratedValue(generator = "UUID")

    // OLD Deprecated ways of doing auto ID generation with new Hibernate version:
    // @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    // New implementation
    @UuidGenerator
    private UUID id;

    @Version
    private Integer version;  // Mostly to protect against concurrent modifications

    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
