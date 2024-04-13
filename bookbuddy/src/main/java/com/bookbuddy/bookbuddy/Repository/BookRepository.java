package com.bookbuddy.bookbuddy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.Book;


public interface BookRepository extends JpaRepository<Book, Long>{
    Book findByIsbn(String isbn);
}
