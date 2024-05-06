package com.bookbuddy.bookbuddy.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.Entities.BookCollectionDTO;
import com.bookbuddy.bookbuddy.ServiceClasses.BookCollectionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


// This is the Book Collection Controller. It is a REST Controller that handles HTTP requests related to book collections.
// It has endpoints for getting a collection by ID, deleting a collection by ID, adding a new collection, renaming a collection, adding a book to a collection, and removing a book from a collection.
// Jimmy Karoly

@RestController
@Tag(name = "Book Collection Controller", description = "Endpoints for Book Collection")
@RequestMapping("/collections")
public class BookCollectionController {

    @Autowired
    BookCollectionService bCService;

    @GetMapping("/{collectionId}")
    @Operation(summary="Get collection by id")
    public ResponseEntity<BookCollectionDTO> getCollection(
        @Parameter(description="Unique ID corresponding to a collection", example="1") @PathVariable Long collectionId) 
    {
        BookCollectionDTO collection = bCService.getCollectionById(collectionId);
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/get/{userId}")
    @Operation(summary="Get all collections from a user")
    public ResponseEntity<List<BookCollectionDTO>> getAllCollectionsByUser(
        @Parameter(description="Unique ID corresponding to a user", example="1") @PathVariable Long userId) 
    {
        List<BookCollectionDTO> collections = bCService.getAllFromUser(userId);
        return ResponseEntity.ok(collections);
    }
    

    @DeleteMapping("/{collectionId}")
    @Operation(summary="Delete collection by id")
    public ResponseEntity<String> deleteCollection(
        @Parameter(description="Unique ID corresponding to a collection", example="1") @PathVariable Long collectionId) 
    {
        bCService.deleteCollection(collectionId);
        return ResponseEntity.ok("Collection with id: " + collectionId + " was deleted");
    }

    @PostMapping("{userId}/new/{name}")
    @Operation(summary="Add a new collection to the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Book Collection added successfully"),
    })
    public ResponseEntity<BookCollectionDTO> addCollection(
        @Parameter(description="Unique ID corresponding to a user", example="1") @PathVariable Long userId, 
        @Parameter(description="What the user wants to name the new collection", example="Favorite Books") @PathVariable String name)
    {
        BookCollectionDTO collection = bCService.createCollection(userId, name);
        return ResponseEntity.status(HttpStatus.CREATED).body(collection);
    }

    @PutMapping("{collectionId}/rename/{newName}")
    @Operation(summary="Rename a collection")
    public ResponseEntity<BookCollectionDTO> renameCollection(
        @Parameter(description="Unique ID corresponding to a collection", example="1") @PathVariable Long collectionId, 
        @Parameter(description="What the user wants to rename to collection to", example="Books I've Read") @PathVariable String newName)
    {
            BookCollectionDTO collection = bCService.renameCollection(collectionId, newName);
            return ResponseEntity.ok(collection);
    }

    @PutMapping("{collectionId}/add_book/{bookId}")
    @Operation(summary="Add a book to a collection")
    public ResponseEntity<BookCollectionDTO> addBook(
        @Parameter(description="Unique ID corresponding to a collection", example="1") @PathVariable Long collectionId, 
        @Parameter(description="Unique ID corresponding to a book", example="1") @PathVariable Long bookId)
    {
        BookCollectionDTO collection = bCService.addBook(collectionId, bookId);
        return ResponseEntity.ok(collection);
    }

    @PutMapping("{collectionId}/remove_book/{bookId}")
    @Operation(summary="Remove a book from a collection")
    public ResponseEntity<BookCollectionDTO> removeBook(
        @Parameter(description="Unique ID corresponding to a collection", example="1") @PathVariable Long collectionId, 
        @Parameter(description="Unique ID corresponding to a book", example="1") @PathVariable Long bookId)
    {
        BookCollectionDTO collection = bCService.removeBook(collectionId, bookId);
        return ResponseEntity.ok(collection);
    }


    
}
