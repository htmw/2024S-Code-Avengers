package com.bookbuddy.bookbuddy.Entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
public @Data class BookCollection {
    private @Id @GeneratedValue Long id;
    private String collectionName;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Book> books;



}


