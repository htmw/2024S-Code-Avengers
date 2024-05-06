package com.bookbuddy.bookbuddy.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    default User findByEmail(String email) {
        return Optional.ofNullable(findByEmailIgnoreCase(email))
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }
   
    User findByEmailIgnoreCase(String email);
}
