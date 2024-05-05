package com.bookbuddy.bookbuddy.CreatedExceptions;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(Long orderId){
        super("Could not find order: " + orderId);
    }
    
}
