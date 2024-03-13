package com.bookbuddy.bookbuddy.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.Book;


interface BookRepository extends JpaRepository<Book, Long>{
    
}
