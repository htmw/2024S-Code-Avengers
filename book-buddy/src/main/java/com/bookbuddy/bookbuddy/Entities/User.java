package com.bookbuddy.bookbuddy.Entities;

import lombok.Data;

public @Data class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String pathToProfilePicture;

}
