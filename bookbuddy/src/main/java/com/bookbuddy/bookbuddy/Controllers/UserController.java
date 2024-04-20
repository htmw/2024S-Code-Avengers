package com.bookbuddy.bookbuddy.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Entities.UserDTO;
import com.bookbuddy.bookbuddy.Repository.UserRepository;
import com.bookbuddy.bookbuddy.ServiceClasses.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")

public class UserController {
    @Autowired
    private final UserRepository uRepository;
    @Autowired
    private final UserService uService;

    public UserController(UserRepository uRepository, UserService uService){
        this.uRepository = uRepository;
        this.uService = uService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> all(){
        List<User> users = uRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/addNew")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<User> newUser(@RequestBody User newUser) {
    	uService.addNewUser(newUser);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> findUser(@PathVariable Long userId) {
        UserDTO user = uService.getUserDetails(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUserDetails) {
        User updatedUser = uService.updateUser(userId, updatedUserDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        boolean deleted = uService.deleteUser(userId);
        if(deleted) return ResponseEntity.ok("User successfully deleted");
        else return ResponseEntity.notFound().build();

    }

}


