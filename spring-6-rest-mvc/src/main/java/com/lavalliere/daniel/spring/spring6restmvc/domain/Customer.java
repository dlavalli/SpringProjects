package com.lavalliere.daniel.spring.spring6restmvc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    // @GeneratedValue(strategy = GenerationType.IDENTITY) // For long id type
    // OLD Deprecated ways of doing auto ID generation with new Hibernate version:
    // @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Id
    @Column(length=36, columnDefinition = "varchar", updatable = false, nullable = false)  // Hints to sql creation
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @Version
    private Integer version;  // Mostly to protect against concurrent modifications

    @NotNull
    @NotBlank
    @Size(max=50) // Jakarta constraints
    @Column(length = 50)  // Hibernate constraint
    private String name;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
