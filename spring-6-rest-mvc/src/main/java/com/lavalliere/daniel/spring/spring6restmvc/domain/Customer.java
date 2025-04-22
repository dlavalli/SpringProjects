package com.lavalliere.daniel.spring.spring6restmvc.domain;

import jakarta.persistence.*;
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

    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
