package com.bookbuddy.bookbuddy.Entities;

import lombok.Data;

public @Data class User {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String dateOfBirth;
    private final String pathToProfilePicture;

}
