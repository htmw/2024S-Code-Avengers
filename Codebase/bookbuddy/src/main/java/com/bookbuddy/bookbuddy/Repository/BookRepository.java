package com.bookbuddy.bookbuddy.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookbuddy.bookbuddy.Entities.Book;


public interface BookRepository extends JpaRepository<Book, Long>{

    @Query("SELECT b FROM Book b WHERE b.title = :title AND b.author = :author")
    Book findByTitleAndAuthor(String title, String author);

    @Override
    Page<Book> findAll(Pageable pageable);
}
