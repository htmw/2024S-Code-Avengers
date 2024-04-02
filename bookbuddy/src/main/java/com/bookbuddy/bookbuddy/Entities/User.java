package com.bookbuddy.bookbuddy.Entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    @Column(name="firebaseUID")
    private Long firebaseUID;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="date_of_birth")
    private String dateOfBirth;

    @OneToMany(mappedBy="user", cascade= CascadeType.ALL)
    private List<BookCollection> bookCollections;

    public User(Long firebaseUID, String firstName, String lastName, String email, String dateOfBirth){
        this.firebaseUID = firebaseUID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public User(){
        
    }
    
    public BookCollection findCollectionById(Long collectionId) {
        return this.bookCollections.stream()
                .filter(collection -> collection.getId().equals(collectionId))
                .findFirst()
                .orElse(null);
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getFirebaseUID() {
        return firebaseUID;
    }

    public void setFirebaseUID(Long firebaseUID) {
        this.firebaseUID = firebaseUID;
    }

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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<BookCollection> getBookCollections() {
        return bookCollections;
    }

    public void setBookCollections(List<BookCollection> bookCollections) {
        this.bookCollections = bookCollections;
    }

}
