package com.bookbuddy.bookbuddy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.BookCollection;

public interface BookCollectionRepository extends JpaRepository<BookCollection, Long>{
    
}