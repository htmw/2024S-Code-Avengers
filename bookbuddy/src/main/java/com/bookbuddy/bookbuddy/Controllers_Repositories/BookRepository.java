package com.bookbuddy.bookbuddy.Controllers_Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.Book;


interface BookRepository extends JpaRepository<Book, Long>{
    
}
