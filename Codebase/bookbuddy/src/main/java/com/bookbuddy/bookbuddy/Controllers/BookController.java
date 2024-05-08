package com.bookbuddy.bookbuddy.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin("*")
public class BookController {
    
    @Autowired
    BookRepository bookRepository;

    @GetMapping("/all")
    @Operation(summary="Get all books")
    public ResponseEntity<List<Book>> getAll() 
    {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/popular")
    @Operation(summary="Get most popular books")
    public ResponseEntity<List<Book>> getFirst20() 
    {
        Pageable first20 = PageRequest.of(0, 20);
        Page<Book> bookPage = bookRepository.findAll(first20);
        List<Book> books = bookPage.getContent();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{bookId}")
    @Operation(summary="Get book by id")
    public ResponseEntity<Book> findBook(
        @Parameter(description="Unique ID corresponding to a book", example="1") @PathVariable Long bookId) 
    {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new BookNotFoundException(bookId));
        
        return ResponseEntity.ok(book);
    }

    // @GetMapping("/search/{bookName}")
    // @Operation(summary="Search for book by title")
    // public ResponseEntity<Book> searchBook(
    //     @Parameter(description="Title of book to search for", example="The Great Gatsby") @PathVariable String bookName)
    // {
    //     Book book = bookRepository.findByTitle(bookName);
    //     return ResponseEntity.ok(book);
    // }
    // @GetMapping("/search/{searchTerm}")
    // @Operation(summary = "Search for book by title")
    // public ResponseEntity<Book> searchBook(
    //     @Parameter(name = "searchTerm", description = "Title of book to search for", example = "The Great Gatsby")
    //     @PathVariable String searchTerm
    // ) {
    //     Pageable pageable = PageRequest.of(0, 1);
    //     Page<Book> bookPage = bookRepository.findDistinctByTitle(searchTerm, pageable);
    //     Book book = bookPage.getContent().isEmpty() ? null : bookPage.getContent().get(0);
        
    //     if (book != null) {
    //         return ResponseEntity.ok(book);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @GetMapping("/search/{searchTerm:.+}")
    @Operation(summary = "Search for book by title")
    public ResponseEntity<Book> searchBook(
        @Parameter(name = "searchTerm", description = "Title of book to search for", example = "The Great Gatsby")
        @PathVariable("searchTerm") String bookName
    ) {
    Pageable pageable = PageRequest.of(0, 1);
    Page<Book> bookPage = bookRepository.findDistinctByTitle(bookName, pageable);
    Book book = bookPage.getContent().isEmpty() ? null : bookPage.getContent().get(0);
    
    if (book != null) {
        return ResponseEntity.ok(book);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    
    
    
    
}
