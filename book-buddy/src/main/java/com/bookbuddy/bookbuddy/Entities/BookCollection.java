package com.bookbuddy.bookbuddy.Entities;

import java.util.List;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
public @Data class BookCollection {
    private String userName;
    private String collectionName;
    private List<Book> books;



}


