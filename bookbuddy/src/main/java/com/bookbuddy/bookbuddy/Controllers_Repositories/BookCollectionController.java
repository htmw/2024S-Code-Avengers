package com.bookbuddy.bookbuddy.Controllers_Repositories;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Service_Classes.BookCollectionService;

@RestController
public class BookCollectionController {

    private final BookCollectionRepository bCRepository;
    private final UserRepository uRepository;
    private final BookCollectionService bCService;

    BookCollectionController(BookCollectionRepository bCRepository, UserRepository uRepository, BookCollectionService bCService){
        this.bCRepository = bCRepository;
        this.uRepository = uRepository;
        this.bCService = bCService;
    }

    @GetMapping("/{userId}/collections/{collectionId}")
    public BookCollection getCollection(Long userId, Long collectionId) {
        Optional<User> userOptional = uRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return user.findCollectionById(collectionId);
        }
        else throw new UserNotFoundException(userId);
    }

    @DeleteMapping("/{userId}/collections/{collectionId}")
    public ResponseEntity<Void> deleteCollection(
            @PathVariable Long userId,
            @PathVariable Long collectionId) {
        boolean deleted = bCService.deleteCollection(userId, collectionId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    
}
