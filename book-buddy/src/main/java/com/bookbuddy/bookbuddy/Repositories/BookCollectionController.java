package com.bookbuddy.bookbuddy.Repositories;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookCollectionController {

    private final BookCollectionRepository repository;

    BookCollectionController(BookCollectionRepository repository){
        this.repository = repository;
    }


    
}
