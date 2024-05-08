package com.bookbuddy.bookbuddy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.Entities.User;

public interface BookCollectionRepository extends JpaRepository<BookCollection, Long>{
    BookCollection findByUserAndCollectionName(User user, String name);
}