package com.bookbuddy.bookbuddy.CreatedExceptions;

public class CollectionNotFoundException extends RuntimeException{
    
    public CollectionNotFoundException(Long id){
        super("Collection with id: " + id + " not found.");
    }
}
