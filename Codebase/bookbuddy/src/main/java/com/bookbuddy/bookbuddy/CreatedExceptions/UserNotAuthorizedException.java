package com.bookbuddy.bookbuddy.CreatedExceptions;

public class UserNotAuthorizedException extends RuntimeException {
    
    public UserNotAuthorizedException(Long id) {
        super("User cannot access this resource " + id);
    }
}
