package com.bookbuddy.bookbuddy.Entities;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
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

// This is the BookCollection Entity. It represents a collection of books in our application.
// It has a unique ID generated by the database, a collection name, a user, and a list of books.
// For returning book collection information to the client, we use the BookCollectionDTO class.
// Jimmy Karoly

@Entity
@Schema(name = "BookCollection", description = "collection of books")
@Table(name= "book_collections")
public class BookCollection {
    @Id 
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique ID generated by database corresponding to a book collection", example = "1")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @Schema(description = "name of the collection")
    @Column(name = "collection_name") 
    private String collectionName;

    @ManyToOne
    @Schema(description = "id of the user")
    @JoinColumn(name = "user_id", referencedColumnName= "id")
    private User user;

    @ManyToMany
    @Schema(description="List of Books")
    @JoinTable(
        name = "collection_books",
        joinColumns=@JoinColumn(name = "collection_id"),
        inverseJoinColumns=@JoinColumn(name="book_id")
    )
    private List<Book> books;

    public BookCollection(){
        
    }
    public BookCollection(User user, String name){
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

    public void addBook(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
    }

    public void removeBook(Book book) {
        if (books != null) {
            books.remove(book);
        }
    }

    public void changeBookOrder(int currentIndex, int newIndex) {
        if (currentIndex < 0 || currentIndex >= books.size() || newIndex < 0 || newIndex >= books.size()) {
            throw new IllegalArgumentException("Invalid index");
        }
        
        Book bookToMove = books.remove(currentIndex);
        books.add(newIndex, bookToMove);
    }



}


