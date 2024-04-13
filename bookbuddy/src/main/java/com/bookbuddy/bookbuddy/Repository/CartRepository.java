package com.bookbuddy.bookbuddy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	Cart findById(long cartId);

}
