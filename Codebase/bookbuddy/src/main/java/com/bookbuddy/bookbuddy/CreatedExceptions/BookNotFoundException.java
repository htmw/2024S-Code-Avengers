package com.bookbuddy.bookbuddy.CreatedExceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long bookId) {
        super("Could not find book " + bookId);
    }

}
