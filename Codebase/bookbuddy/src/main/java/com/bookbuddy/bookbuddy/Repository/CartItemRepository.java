package com.bookbuddy.bookbuddy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
