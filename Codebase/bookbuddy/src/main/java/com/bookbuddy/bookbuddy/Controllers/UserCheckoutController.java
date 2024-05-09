package com.bookbuddy.bookbuddy.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.Entities.CartItem;
import com.bookbuddy.bookbuddy.Entities.UserCheckout;
import com.bookbuddy.bookbuddy.Repository.CartItemRepository;
import com.bookbuddy.bookbuddy.ServiceClasses.UserCheckoutService;

@RestController
@RequestMapping("/user-checkout")
@CrossOrigin("*")
public class UserCheckoutController {

    @Autowired
    UserCheckoutService userCheckoutService;
    @Autowired
    CartItemRepository cartItemRepository;

    @PostMapping
    public ResponseEntity<UserCheckout> createUserCheckout(@RequestBody UserCheckout userCheckout) {
        UserCheckout createdUserCheckout = userCheckoutService.createUserCheckout(userCheckout);
        List<CartItem> cartItems = userCheckout.getCartItems();
        for (CartItem cartItem : cartItems) {
            cartItemRepository.delete(cartItem);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserCheckout);
    }

}
