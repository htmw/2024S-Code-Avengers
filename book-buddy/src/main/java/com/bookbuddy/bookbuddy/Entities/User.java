package com.bookbuddy.bookbuddy.Entities;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
public @Data class User {
    private @Id long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String pathToProfilePicture;

}
