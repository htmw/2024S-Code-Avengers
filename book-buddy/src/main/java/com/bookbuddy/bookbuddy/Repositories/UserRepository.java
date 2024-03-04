package com.bookbuddy.bookbuddy.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.User;

interface UserRepository extends JpaRepository<User, Long>{
    
}
