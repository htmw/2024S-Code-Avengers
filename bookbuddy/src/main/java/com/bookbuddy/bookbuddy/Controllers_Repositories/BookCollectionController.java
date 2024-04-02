package com.bookbuddy.bookbuddy.Controllers_Repositories;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Book;
import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Service_Classes.BookCollectionService;


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

    @GetMapping("/{userId}/{collectionId}")
    public BookCollection getCollection(Long userId, Long collectionId) {
        Optional<User> userOptional = uRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return user.findCollectionById(collectionId);
        }
        else throw new UserNotFoundException(userId);
    }

    @DeleteMapping("/{userId}/{collectionId}")
    public boolean deleteCollection(
            @PathVariable Long userId,
            @PathVariable Long collectionId) {
        boolean deleted = bCService.deleteCollection(userId, collectionId);
        return deleted;
    }

    @PostMapping("{userId}/addCollection/{name}")
    public BookCollection addCollection(@PathVariable Long userId, @PathVariable String name){
        return bCService.createCollection(userId, name);
    }

    @PutMapping("{userId}/{collectionId}/rename/{name}")
    public BookCollection updateCollection(@PathVariable Long userId, @PathVariable Long collectionId, @PathVariable String newName){
            return bCService.renameCollection(userId, collectionId, newName);
    }

    @PutMapping("{userId}/{collectionId}/addBook")
    public BookCollection addBook(@PathVariable Long userId, @PathVariable Long collectionId, @RequestBody Book book){
        return bCService.addBook(userId, collectionId, book);
    }

    @PutMapping("{userId}/{collectionId}/removeBook")
    public BookCollection removeBook(@PathVariable Long userId, @PathVariable Long collectionId, @RequestBody Book book){
        return bCService.removeBook(userId, collectionId, book);
    }


    
}