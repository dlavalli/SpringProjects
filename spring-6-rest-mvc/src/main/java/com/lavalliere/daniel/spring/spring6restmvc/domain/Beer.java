package com.lavalliere.daniel.spring.spring6restmvc.domain;

import com.lavalliere.daniel.spring.spring6restmvc.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Beer {

    // @GeneratedValue(strategy = GenerationType.IDENTITY) // For long id type
    // OLD Deprecated ways of doing auto ID generation with new Hibernate version:
    // @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Id
    @Column(length=36, columnDefinition = "varchar(36)", updatable = false, nullable = false)  // Hints to sql creation
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @Version
    private Integer version;  // Mostly to protect against concurrent modifications

    @NotNull
    @NotBlank
    @Size(max=50) // Jakarta constraints
    @Column(length = 50)  // Hibernate constraint
    private String beerName;

    @NotNull
    @JdbcTypeCode(value = SqlTypes.SMALLINT)
    private BeerStyle beerStyle;

    @NotNull
    @NotBlank
    @Size(max=255) // Jakarta constraints
    @Column(length = 255)  // Hibernate constraint
    private String upc;

    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Builder.Default
    @OneToMany(mappedBy = "beer")
    private Set<BeerOrderLine> beerOrderLine = new HashSet<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "beer_category",
        joinColumns = @JoinColumn(name = "beer_id"),            // id field from the beer table
        inverseJoinColumns = @JoinColumn(name = "category_id")) // id field from the category table
    private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category){
        this.categories.add(category);
        category.getBeers().add(this);
    }

    public void removeCategory(Category category){
        this.categories.remove(category);
        category.getBeers().remove(category);
    }
}
