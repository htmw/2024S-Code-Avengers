package com.bookbuddy.bookbuddy.Controllers_Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Entities.UserDTO;
import com.bookbuddy.bookbuddy.Service_Classes.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository uRepository;
    private final UserService uService;

    public UserController(UserRepository uRepository, UserService uService){
        this.uRepository = uRepository;
        this.uService = uService;
    }

    @GetMapping()
    public List<User> all(){
        return uRepository.findAll();
    }

    @PostMapping("/addNew")
    public User newUser(@RequestBody User newUser) {
        return uService.addNewUser(newUser);
    }

    @GetMapping("/{userId}")
    public UserDTO findUser(@PathVariable Long userId) {
        return uService.getUserDetails(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUserDetails) {
        User updatedUser = uService.updateUser(userId, updatedUserDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id){
        Optional<User> userOptional = uRepository.findById(id);
        if (userOptional.isPresent()) {
            uRepository.deleteById(id);
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}


