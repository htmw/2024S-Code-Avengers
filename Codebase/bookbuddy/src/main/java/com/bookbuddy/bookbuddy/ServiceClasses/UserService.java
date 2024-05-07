package com.bookbuddy.bookbuddy.ServiceClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Cart;
import com.bookbuddy.bookbuddy.Entities.CreateUserDTO;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Entities.UserDTO;
import com.bookbuddy.bookbuddy.Repository.CartRepository;
import com.bookbuddy.bookbuddy.Repository.UserRepository;

// This is the User Service. It is a service class that handles the business logic for users.
// It has methods for getting user details, updating user details, adding a new user, and deleting a user.
// Jimmy Karoly

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;

    public UserDTO getUserDetails(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        
        return UserDTO.fromEntity(user);
    }

    public UserDTO updateUser(Long userId, UserDTO updatedUser){
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if(updatedUser.getEmail() != null) existingUser.setEmail(updatedUser.getEmail());
        if(updatedUser.getFirstName() != null) existingUser.setFirstName(updatedUser.getFirstName());
        if(updatedUser.getLastName() != null) existingUser.setLastName(updatedUser.getLastName());
        if(updatedUser.getDateOfBirth() != null) existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
        userRepository.save(existingUser);

        return UserDTO.fromEntity(existingUser);
    }

    public UserDTO addNewUser(CreateUserDTO newUserDetails) {
        User user = new User(newUserDetails.getFirstName(), newUserDetails.getLastName(), newUserDetails.getEmail(), newUserDetails.getDateOfBirth());
        user = userRepository.save(user);

        Cart newCart = new Cart();
		newCart.setUser(user);
        newCart = cartRepository.save(newCart);

        user.setCart(newCart);
        user = userRepository.save(user);
        
        return UserDTO.fromEntity(user);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public UserDTO getUserDetailsWithEmail(String email) {
        User user = userRepository.findByEmailIgnoreCase(email);
        return UserDTO.fromEntity(user);
    }


}
