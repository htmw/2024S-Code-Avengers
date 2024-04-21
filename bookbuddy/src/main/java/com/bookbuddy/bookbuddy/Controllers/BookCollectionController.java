package com.bookbuddy.bookbuddy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.ServiceClasses.BookCollectionService;


@RestController
@RequestMapping("/collections")
public class BookCollectionController {

    @Autowired
    private final BookCollectionService bCService;

    BookCollectionController(BookCollectionService bCService){
        this.bCService = bCService;
    }

    @GetMapping("/{collectionId}")
    public ResponseEntity<BookCollection> getCollection(@PathVariable Long collectionId) {
        BookCollection collection = bCService.getCollectionById(collectionId);
        return ResponseEntity.ok(collection);
    }

    @DeleteMapping("/{collectionId}")
    public ResponseEntity<String> deleteCollection(@PathVariable Long collectionId) {
        bCService.deleteCollection(collectionId);
        return ResponseEntity.ok("Collection with id: " + collectionId + " was deleted");
    }

    @PostMapping("{userId}/addCollection/{name}")
    public ResponseEntity<BookCollection> addCollection(@PathVariable Long userId, @PathVariable String name){
        BookCollection collection = bCService.createCollection(userId, name);
        return ResponseEntity.ok(collection);
    }

    @PutMapping("{collectionId}/rename/{newName}")
    public ResponseEntity<BookCollection> renameCollection(@PathVariable Long collectionId, @PathVariable String newName){
            BookCollection collection = bCService.renameCollection(collectionId, newName);
            return ResponseEntity.ok(collection);
    }

    @PutMapping("{collectionId}/addBook/{bookId}")
    public ResponseEntity<BookCollection> addBook(@PathVariable Long collectionId, @PathVariable Long bookId){
        BookCollection collection = bCService.addBook(collectionId, bookId);
        return ResponseEntity.ok(collection);
    }

    @PutMapping("{collectionId}/removeBook/{bookId}")
    public ResponseEntity<BookCollection> removeBook(@PathVariable Long collectionId, @PathVariable Long bookId){
        BookCollection collection = bCService.removeBook(collectionId, bookId);
        return ResponseEntity.ok(collection);
    }


    
}
