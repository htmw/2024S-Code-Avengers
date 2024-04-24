package com.bookbuddy.bookbuddy.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.CreatedExceptions.BookNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Book;
import com.bookbuddy.bookbuddy.Repository.BookRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

// This is the Book Controller. It is a REST Controller that handles HTTP requests related to books.
// It has endpoints for listing all books and getting book details by ID.
// Jimmy Karoly

@RestController
@Tag(name = "Book Controller", description = "Endpoints for Books")
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    BookRepository bookRepository;

    @GetMapping()
    @Operation(summary="Get all books")
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{bookId}")
    @Operation(summary="Get book by id")
    public ResponseEntity<Book> findBook(@Parameter(description="Unique ID corresponding to a book", example="1") @PathVariable Long bookId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new BookNotFoundException(bookId));
        
        return ResponseEntity.ok(book);
    }

    
    
    
    
}
