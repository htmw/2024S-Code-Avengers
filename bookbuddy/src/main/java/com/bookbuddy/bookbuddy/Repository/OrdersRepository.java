package com.bookbuddy.bookbuddy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.Order;

public interface OrdersRepository extends JpaRepository<Order, Long>{

	Order findById(long orderId);

}
