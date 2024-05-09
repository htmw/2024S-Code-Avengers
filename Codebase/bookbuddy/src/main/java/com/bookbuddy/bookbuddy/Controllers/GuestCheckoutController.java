package com.bookbuddy.bookbuddy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.Entities.GuestCheckout;
import com.bookbuddy.bookbuddy.Repository.GuestCheckoutRepository;

@RestController
@RequestMapping("/guestcheckout")
@CrossOrigin("*")
public class GuestCheckoutController {

    @Autowired
    GuestCheckoutRepository guestCheckoutRepository;

    @PostMapping
    public ResponseEntity<GuestCheckout> saveGuestCheckout(@RequestBody GuestCheckout guestCheckout) {
        GuestCheckout savedGuestCheckout = guestCheckoutRepository.save(guestCheckout);
        return new ResponseEntity<>(savedGuestCheckout, HttpStatus.CREATED);
    }
}
