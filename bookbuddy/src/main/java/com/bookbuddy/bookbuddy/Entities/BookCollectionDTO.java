package com.bookbuddy.bookbuddy.Entities;

import java.util.List;

public class BookCollectionDTO {
    private Long id;
    private String collectionName;
    private List<Book> booksInCollection;

    public BookCollectionDTO(Long id, String collectionName, List<Book> booksInCollection){
        this.id = id;
        this.collectionName = collectionName;
        this.booksInCollection = booksInCollection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public List<Book> getBooksInCollection() {
        return booksInCollection;
    }

    public void setBooksInCollection(List<Book> booksInCollection) {
        this.booksInCollection = booksInCollection;
    }
    
}
