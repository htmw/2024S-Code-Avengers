package com.bookbuddy.bookbuddy.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.Entities.User;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
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
}
