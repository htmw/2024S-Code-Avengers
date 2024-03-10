package com.bookbuddy.bookbuddy.Entities;

import java.util.ArrayList;
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
    private Long userId;

    @ManyToMany
    private List<Book> books;


    public BookCollection(Long userId, String name){
        this.id = 0l; //Fix this, pick a strategy to generate IDs
        this.userId = userId;
        this.collectionName = name;
        this.books = new ArrayList<>();
    }


}


