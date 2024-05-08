package com.bookbuddy.bookbuddy.ServiceClasses;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Cart;
import com.bookbuddy.bookbuddy.Entities.CreateUserDTO;
import com.bookbuddy.bookbuddy.Entities.Genre;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Entities.UserDTO;
import com.bookbuddy.bookbuddy.Repository.CartRepository;
import com.bookbuddy.bookbuddy.Repository.GenreRepository;
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
    @Autowired
    GenreRepository genreRepository;

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
        user.setUserDescription(newUserDetails.getDescription());
        user = userRepository.save(user);
    
        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart = cartRepository.save(newCart);
        user.setCart(newCart);
        user = userRepository.save(user);
    
        // Convert the comma-separated genres string to a list
        List<String> genresList = newUserDetails.getGenresList();
    
        // Associate the genres with the user
        associateGenresWithUser(user.getId(), genresList);
    
        return UserDTO.fromEntity(user);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public UserDTO getUserDetailsWithEmail(String email) {
        User user = userRepository.findByEmailIgnoreCase(email);
        return UserDTO.fromEntity(user);
    }

    public void associateGenresWithUser(Long userId, List<String> genreNames) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Set<Genre> genres = new HashSet<>();

        for (String genreName : genreNames) {
            Genre genre = genreRepository.findByName(genreName);
            if (genre == null) {
                genre = new Genre();
                genre.setName(genreName);
                genreRepository.save(genre);
            }
            genres.add(genre);
        }

        user.setGenres(genres);
        userRepository.save(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream()
            .map(user -> {
                UserDTO userDTO = UserDTO.fromEntity(user);
                return userDTO;
            })
            .collect(Collectors.toList());
        return userDTOs;
    }




}
