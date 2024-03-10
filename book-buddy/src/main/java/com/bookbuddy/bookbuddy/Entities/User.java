package com.bookbuddy.bookbuddy.Entities;

import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
public @Data class User {
    private @Id @GeneratedValue Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String pathToProfilePicture;

    @OneToMany(mappedBy = "user")
    private List<BookCollection> bookCollections;


    public BookCollection findCollectionById(Long collectionId) {
        return this.bookCollections.stream()
                .filter(collection -> collection.getId().equals(collectionId))
                .findFirst()
                .orElse(null);
    }

}
