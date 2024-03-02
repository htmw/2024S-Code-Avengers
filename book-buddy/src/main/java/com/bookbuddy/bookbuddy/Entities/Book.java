package com.bookbuddy.bookbuddy.Entities;

import java.math.BigDecimal;

import lombok.Data;

public @Data class Book {
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
