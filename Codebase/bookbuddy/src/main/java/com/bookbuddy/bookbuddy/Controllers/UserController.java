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

import com.bookbuddy.bookbuddy.Entities.CreateUserDTO;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Entities.UserDTO;
import com.bookbuddy.bookbuddy.Repository.UserRepository;
import com.bookbuddy.bookbuddy.ServiceClasses.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")

public class UserController {
    @Autowired
    UserRepository uRepository;
    @Autowired
    UserService userService;


    @GetMapping("/all")
    @Operation(summary="Admin route to list all users")
    public ResponseEntity<List<User>> all(){
        List<User> users = uRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/addNew")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary="Add a new user to the database")
    public ResponseEntity<UserDTO> newUser(@RequestBody CreateUserDTO newUser) {
    	UserDTO newUserDTO = userService.addNewUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> findUser(@PathVariable Long userId) {
        UserDTO user = userService.getUserDetails(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO updatedUserDetails) {
        UserDTO updatedUser = userService.updateUser(userId, updatedUserDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User with id: " + userId + " was deleted");
    }

}


