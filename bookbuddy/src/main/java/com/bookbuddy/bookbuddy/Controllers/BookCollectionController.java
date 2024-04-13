package com.bookbuddy.bookbuddy.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.Entities.Book;
import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.Repository.BookCollectionRepository;
import com.bookbuddy.bookbuddy.Repository.UserRepository;
import com.bookbuddy.bookbuddy.ServiceClasses.BookCollectionService;


@RestController
@RequestMapping("/collections")
public class BookCollectionController {

    private final BookCollectionRepository bCRepository;
    private final UserRepository uRepository;
    private final BookCollectionService bCService;

    BookCollectionController(BookCollectionRepository bCRepository, UserRepository uRepository, BookCollectionService bCService){
        this.bCRepository = bCRepository;
        this.uRepository = uRepository;
        this.bCService = bCService;
    }

    // @GetMapping("/{userId}/{collectionId}")
    // public ResponseEntity<BookCollection> getCollection(@PathVariable Long userId, @PathVariable Long collectionId) {
    //     Optional<User> userOptional = uRepository.findById(userId);
    //     if(userOptional.isPresent()){
    //         User user = userOptional.get();
    //         BookCollection collection = user.findCollectionById(collectionId);
    //         return ResponseEntity.ok(collection);
    //     }
    //     else throw new UserNotFoundException(userId);
    // }

    @GetMapping("/{collectionId}")
    public ResponseEntity<BookCollection> getCollection(@PathVariable Long collectionId) {
        BookCollection collection = bCService.getCollectionById(collectionId);
        return ResponseEntity.ok(collection);
    }

    @DeleteMapping("/{collectionId}")
    public ResponseEntity<String> deleteCollection(
            @PathVariable Long userId,
            @PathVariable Long collectionId) {
        boolean deleted = bCService.deleteCollection(collectionId);
        if(deleted) return ResponseEntity.ok("Collection successfully deleted");
        else return ResponseEntity.notFound().build();
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
