package com.bookbuddy.bookbuddy.Controllers_Repositories;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.CreatedExceptions.BookNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Book;



@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository repository;

    BookController(BookRepository repository){
        this.repository = repository;
    }

    @GetMapping()
    List<Book> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Book findBook(@PathVariable Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
    }

    
    
    
    
}
