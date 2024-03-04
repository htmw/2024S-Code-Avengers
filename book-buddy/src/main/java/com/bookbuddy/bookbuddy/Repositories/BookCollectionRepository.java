package com.bookbuddy.bookbuddy.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.BookCollection;

interface BookCollectionRepository extends JpaRepository<BookCollection, Long>{
    
}