package com.lavalliere.daniel.spring.apiversioning.domain;

import com.lavalliere.daniel.spring.apiversioning.dto.UserDTOv1;
import com.lavalliere.daniel.spring.apiversioning.dto.UserDTOv2;
import lombok.Builder;

@Builder
public record User(Integer id, String name, String email, String website) {  // Could be additional fields you do not want to provide in the response compared to DTO
    public UserDTOv1 toDTOv1() {
        return UserDTOv1.builder().id(id).name(name).email(email).website(website).build();
    }

    public UserDTOv2 toDTOv2() {
        String[] nameParts = name.split(" ");
        return UserDTOv2.builder().id(id).firstName(nameParts[0]).lastName(nameParts[1]) .email(email).build();
    }
}
