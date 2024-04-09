package com.bookbuddy.bookbuddy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long>{

	Orders findById(long orderId);

}
