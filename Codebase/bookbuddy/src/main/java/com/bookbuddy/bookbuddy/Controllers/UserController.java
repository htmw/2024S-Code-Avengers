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
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.Entities.CreateUserDTO;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Entities.UserDTO;
import com.bookbuddy.bookbuddy.Repository.UserRepository;
import com.bookbuddy.bookbuddy.ServiceClasses.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

// This is the User Controller. It is a REST Controller that handles HTTP requests related to users.
// It has endpoints for listing all users, adding a new user, getting user details by ID, updating user details by ID, and deleting a user by ID.
// Jimmy Karoly

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Endpoints for Users")
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

    @PostMapping("/new")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User added successfully"),
    })
    @Operation(summary="Add a new user to the database")
    public ResponseEntity<UserDTO> newUser(@RequestBody CreateUserDTO newUser) {
    	UserDTO newUserDTO = userService.addNewUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserDTO);
    }

    @GetMapping("/{userId}")
    @Operation(summary="Get user details by id")
    public ResponseEntity<UserDTO> findUser(@Parameter(description="Unique ID corresponding to a user", example="1") @PathVariable Long userId) {
        UserDTO user = userService.getUserDetails(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    @Operation(summary="Update user details by id")
    public ResponseEntity<UserDTO> updateUser(@Parameter(name="userId", description="Unique ID corresponding to a user", example="1") @PathVariable Long userId, @RequestBody UserDTO updatedUserDetails) {
        UserDTO updatedUser = userService.updateUser(userId, updatedUserDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary="Delete user by id")
    public ResponseEntity<String> deleteUser(@Parameter(name="userId", description="Unique ID corresponding to a user", example="1") @PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User with id: " + userId + " was deleted");
    }

}


