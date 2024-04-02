package com.bookbuddy.bookbuddy.Service_Classes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.Controllers_Repositories.BookCollectionRepository;
import com.bookbuddy.bookbuddy.Controllers_Repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository uRepository;
    private final BookCollectionRepository bCRepository;

    public UserService(UserRepository repository, BookCollectionRepository bRepository){
        this.uRepository = repository;
        this.bCRepository = bRepository;
    }
    
    public List<String> getRecommendations(){
        //Call to ML API

        //Get Recommendations and store
        
        return null;
    }


}
