package com.bookbuddy.bookbuddy.Entities;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
public @Data class Book {
    private @Id long id;
    private String title;
    private String author;
    private String ISBN;
    private String pathToCover;
    private String publicationDate;
    private String genre;
    private String description;
    private BigDecimal price;
    private int quantityAvailable;

}
