package com.bookbuddy.bookbuddy.CreatedExceptions;

public class PaymentFailedException extends RuntimeException{
    public PaymentFailedException(String message){
        super(message);
    }
}
