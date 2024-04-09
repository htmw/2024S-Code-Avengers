package com.bookbuddy.bookbuddy.ServiceClasses;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.CreatedExceptions.CollectionNotFoundException;
import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotAuthorizedException;
import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Book;
import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Repository.BookCollectionRepository;
import com.bookbuddy.bookbuddy.Repository.BookRepository;
import com.bookbuddy.bookbuddy.Repository.UserRepository;

@Service
public class BookCollectionService {
    
    private final BookRepository bRepository;
    private final BookCollectionRepository bCRepository;
    private final UserRepository uRepository;

    public BookCollectionService(UserRepository uRepository, BookRepository bRepository, BookCollectionRepository bCRepository){
        this.bRepository = bRepository;
        this.uRepository = uRepository;
        this.bCRepository = bCRepository;
    }

    public boolean deleteCollection(Long userId, Long collectionId) {
        Optional<User> userOptional = uRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<BookCollection> collections = user.getBookCollections();
            collections.removeIf(collection -> collection.getId().equals(collectionId));
            uRepository.save(user);
            return true;
        }
        return false;
    }

    public BookCollection createCollection(Long userId, String name){
        Optional<User> userOptional = uRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            BookCollection books = new BookCollection(user, name);
            return bCRepository.save(books);
        }
        else {
            throw new UserNotFoundException(userId);
        }

    }

    public BookCollection renameCollection(Long userId, Long collectionId, String newName) {
        Optional<User> userOptional = uRepository.findById(userId);
        Optional<BookCollection> collectionOptional = bCRepository.findById(collectionId);
    
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException(userId);
        }
    
        User user = userOptional.get();
    
        if (!collectionOptional.isPresent()) {
            throw new CollectionNotFoundException(collectionId);
        }
    
        BookCollection collection = collectionOptional.get();
    
        if (!collection.getUser().equals(user)) {
            throw new UserNotAuthorizedException(userId);
        }
    
        collection.setCollectionName(newName);
        return bCRepository.save(collection);
    }

    public BookCollection addBook(Long userId, Long collectionId, Book book){
        Optional<User> userOptional = uRepository.findById(userId);
        Optional<BookCollection> collectionOptional = bCRepository.findById(collectionId);
    
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException(userId);
        }
    
        User user = userOptional.get();
    
        if (!collectionOptional.isPresent()) {
            throw new CollectionNotFoundException(collectionId);
        }
    
        BookCollection collection = collectionOptional.get();
    
        if (!collection.getUser().equals(user)) {
            throw new UserNotAuthorizedException(userId);
        }

        collection.addBook(book);
        
        return bCRepository.save(collection);
    }

    public BookCollection removeBook(Long userId, Long collectionId, Book book){
        Optional<User> userOptional = uRepository.findById(userId);
        Optional<BookCollection> collectionOptional = bCRepository.findById(collectionId);
    
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException(userId);
        }
    
        User user = userOptional.get();
    
        if (!collectionOptional.isPresent()) {
            throw new CollectionNotFoundException(collectionId);
        }
    
        BookCollection collection = collectionOptional.get();
    
        if (!collection.getUser().equals(user)) {
            throw new UserNotAuthorizedException(userId);
        }

        collection.removeBook(book);
        
        return bCRepository.save(collection);
    }

    
}
