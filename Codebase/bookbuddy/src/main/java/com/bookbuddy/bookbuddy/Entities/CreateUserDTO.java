package com.bookbuddy.bookbuddy.Entities;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;

// This is the CreateUserDTO class. It is a Data Transfer Object (DTO) that is used to create a user in the application.
// It has a first name, a last name, an email, and a date of birth. When this is sent to the server, a user is created with this information, and the database generates a unique ID for the user.
// Jimmy Karoly

@Schema(name = "CreateUserDTO", description = "A DTO for creating a user")
public class CreateUserDTO {

    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @Schema(description = "Email of the user", example = "test@email.com")
    private String email;

    @Schema(description = "Date of birth of the user", example = "1990-01-01")
    private LocalDate dateOfBirth;

    @Schema(description = "Description of the user", example = "I love reading!")
    private String description;

    @Schema(description = "Comma-separated list of genres the user is interested in", example = "Fiction, Mystery, Thriller")
    private String genres;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getGenresList() {
        return Arrays.stream(genres.split(","))
                     .map(String::trim)
                     .filter(g -> !g.isEmpty())
                     .collect(Collectors.toList());
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
