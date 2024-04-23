package com.bookbuddy.bookbuddy.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	public List<CartItem> findByCart_CartId(long cartId);


}
