package com.bookbuddy.bookbuddy.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.Entities.User;

@Service
public class UserService {
    private final UserRepository repository;
    private final BookCollectionRepository bRepository;

    public UserService(UserRepository repository, BookCollectionRepository bRepository){
        this.repository = repository;
        this.bRepository = bRepository;
    }

    public boolean deleteCollection(Long userId, Long collectionId) {
        Optional<User> userOptional = repository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<BookCollection> collections = user.getBookCollections();
            collections.removeIf(collection -> collection.getId().equals(collectionId));
            repository.save(user);
            return true;
        }
        return false;
    }

    public BookCollection createCollection(Long userId, String name){
        Optional<User> userOptional = repository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            BookCollection books = new BookCollection(userId, name);
            return bRepository.save(books);
        }
        else {
            throw new UserNotFoundException(userId);
        }

    }


}
