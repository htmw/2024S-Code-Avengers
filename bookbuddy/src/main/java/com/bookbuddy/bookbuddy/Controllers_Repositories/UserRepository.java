package com.bookbuddy.bookbuddy.Controllers_Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
