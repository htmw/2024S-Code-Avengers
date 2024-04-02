package com.bookbuddy.bookbuddy.Controllers_Repositories;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Service_Classes.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository repository;
    private final UserService service;

    UserController(UserRepository repository, UserService service){
        this.repository = repository;
        this.service = service;
    }

    @GetMapping()
    List<User> all(){
        return repository.findAll();
    }

    @PostMapping("/addNew")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/{id}")
    User findUser(@PathVariable Long userId) {
        return repository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id){
        repository.deleteById(id);
    }

}


