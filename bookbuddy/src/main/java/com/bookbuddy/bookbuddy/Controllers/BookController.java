package com.bookbuddy.bookbuddy.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.CreatedExceptions.BookNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Book;
import com.bookbuddy.bookbuddy.Repository.BookRepository;



@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository repository;

    BookController(BookRepository repository){
        this.repository = repository;
    }

    @GetMapping()
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = repository.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBook(@PathVariable Long id) {
        Book book = repository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
        
        return ResponseEntity.ok(book);
    }

    
    
    
    
}
