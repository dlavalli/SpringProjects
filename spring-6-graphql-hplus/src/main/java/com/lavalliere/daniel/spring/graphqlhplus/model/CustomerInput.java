package com.lavalliere.daniel.spring.graphqlhplus.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInput {
    // Must match the schema
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    public Customer getEntity() {
        return Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .address(address)
                .city(city)
                .state(state)
                .zipCode(zipCode)
            .build();
    }
}
