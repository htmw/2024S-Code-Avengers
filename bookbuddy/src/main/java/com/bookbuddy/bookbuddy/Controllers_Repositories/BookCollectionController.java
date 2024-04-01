package com.bookbuddy.bookbuddy.Controllers_Repositories;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookCollectionController {

    private final BookCollectionRepository repository;

    BookCollectionController(BookCollectionRepository repository){
        this.repository = repository;
    }


    
}
