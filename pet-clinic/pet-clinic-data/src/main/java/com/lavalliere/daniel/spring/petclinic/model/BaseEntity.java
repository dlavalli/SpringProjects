package com.lavalliere.daniel.spring.petclinic.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Setter
@Getter
@MappedSuperclass   // Tell JPA other class will inherit this, we do not need this mapped to a specific database
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Other values are TABLE, SEQUENCE, AUTO
    private Long id;
}
