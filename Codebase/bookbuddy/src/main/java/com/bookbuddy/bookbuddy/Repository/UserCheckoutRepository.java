package com.bookbuddy.bookbuddy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookbuddy.bookbuddy.Entities.UserCheckout;

public interface UserCheckoutRepository extends JpaRepository<UserCheckout, Long> {

}
