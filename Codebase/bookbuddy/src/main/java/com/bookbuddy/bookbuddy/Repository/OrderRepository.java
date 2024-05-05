package com.bookbuddy.bookbuddy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
