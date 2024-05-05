package com.bookbuddy.bookbuddy.CreatedExceptions;

public class CartItemNotFoundException extends RuntimeException {

    public CartItemNotFoundException(Long cartItemId) {
        super("Could not find cart item: " + cartItemId);
    }
}
