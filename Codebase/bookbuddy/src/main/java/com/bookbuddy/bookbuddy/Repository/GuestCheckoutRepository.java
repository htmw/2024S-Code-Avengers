package com.bookbuddy.bookbuddy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookbuddy.bookbuddy.Entities.GuestCheckout;

@Repository
public interface GuestCheckoutRepository extends JpaRepository<GuestCheckout, Long> {

}