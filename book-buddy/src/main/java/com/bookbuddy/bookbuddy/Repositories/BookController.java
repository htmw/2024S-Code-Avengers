package com.bookbuddy.bookbuddy.Repositories;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.CreatedExceptions.BookNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Book;



@RestController
public class BookController {
    private final BookRepository repository;

    BookController(BookRepository repository){
        this.repository = repository;
    }

    @GetMapping("/books")
    List<Book> getAll() {
        return repository.findAll();
    }

    @PostMapping("/books")
    Book newBook(@RequestBody Book book) {
        return repository.save(book);
    }

    @GetMapping("/books/{id}")
    Book findBook(@RequestParam Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
    }
    
    
    
}
