package com.bookbuddy.bookbuddy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	
}
