package com.bookbuddy.bookbuddy.Entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "book_collections")
public class BookCollection {
    @Id 
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "collection_name") 
    private String collectionName;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName= "id")
    private User user;

    @ManyToMany
    @JoinTable(
        name = "collection_books",
        joinColumns=@JoinColumn(name = "collection_id"),
        inverseJoinColumns=@JoinColumn(name="book_id")
    )
    private List<Book> books;


    public BookCollection(User user, String name){
        this.id = 0l; //Fix this, pick a strategy to generate IDs
        this.user = user;
        this.collectionName = name;
    }

    public Long getId() {
        return id;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


}


