package com.bookbuddy.bookbuddy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long>{
    Genre findByName(String name);
}
