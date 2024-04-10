package com.bookbuddy.bookbuddy.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.Entities.User;






@RestController
public class UserController {
    private final UserRepository repository;
    private final UserService service;

    UserController(UserRepository repository, UserService service){
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/users")
    List<User> all(){
        return repository.findAll();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User findUser(@PathVariable Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("users/{id}")
    User updateUser(@PathVariable Long id, @RequestBody User newUser) {
        return repository.findById(id)
            .map(user -> {
                user.setLastName(newUser.getLastName());
                user.setFirstName(newUser.getFirstName());
                return repository.save(user);
            })
            .orElseGet(() -> {
                newUser.setId(id);
                return repository.save(newUser);
            });
        
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id){
        repository.deleteById(id);
    }

    @GetMapping("/users/{id}/collections/{id}")
    public BookCollection getCollection(Long userId, Long collectionId) {
        Optional<User> userOptional = repository.findById(userId);
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
        boolean deleted = service.deleteCollection(userId, collectionId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    


}

