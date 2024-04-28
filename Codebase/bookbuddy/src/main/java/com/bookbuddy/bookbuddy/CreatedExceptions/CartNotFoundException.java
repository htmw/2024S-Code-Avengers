package com.bookbuddy.bookbuddy.CreatedExceptions;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(Long cartId) {
        super("Could not find cart " + cartId);
    }

}
