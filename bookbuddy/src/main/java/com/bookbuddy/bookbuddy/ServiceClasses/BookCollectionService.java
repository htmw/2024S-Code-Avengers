package com.bookbuddy.bookbuddy.ServiceClasses;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.CreatedExceptions.BookNotFoundException;
import com.bookbuddy.bookbuddy.CreatedExceptions.CollectionNotFoundException;
import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Book;
import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Repository.BookCollectionRepository;
import com.bookbuddy.bookbuddy.Repository.BookRepository;
import com.bookbuddy.bookbuddy.Repository.UserRepository;

@Service
public class BookCollectionService {
    @Autowired
    private final BookRepository bRepository;
    @Autowired
    private final BookCollectionRepository bCRepository;
    @Autowired
    private final UserRepository uRepository;

    public BookCollectionService(UserRepository uRepository, BookRepository bRepository, BookCollectionRepository bCRepository){
        this.bRepository = bRepository;
        this.uRepository = uRepository;
        this.bCRepository = bCRepository;
    }

    public boolean deleteCollection(Long collectionId) {
        Optional<BookCollection> collectionOptional = bCRepository.findById(collectionId);
        if (collectionOptional.isPresent()) {
            bCRepository.deleteById(collectionId);
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

    public BookCollection renameCollection(Long collectionId, String newName) {

        Optional<BookCollection> collectionOptional = bCRepository.findById(collectionId);
    
        if (!collectionOptional.isPresent()) {
            throw new CollectionNotFoundException(collectionId);
        }
    
        BookCollection collection = collectionOptional.get();
    
        collection.setCollectionName(newName);
        return bCRepository.save(collection);
    }

    public BookCollection addBook(Long collectionId, Long bookId){
        Optional<BookCollection> collectionOptional = bCRepository.findById(collectionId);
        Optional<Book> bookOptional = bRepository.findById(bookId);
        if (!collectionOptional.isPresent()) {
            throw new CollectionNotFoundException(collectionId);
        }
        if(!bookOptional.isPresent()){
            throw new BookNotFoundException(bookId);
        }
    
        BookCollection collection = collectionOptional.get();
        Book book = bookOptional.get();

        collection.addBook(book);
        
        return bCRepository.save(collection);
    }

    public BookCollection removeBook(Long collectionId, Long bookId){
        Optional<BookCollection> collectionOptional = bCRepository.findById(collectionId);
        Optional<Book> bookOptional = bRepository.findById(bookId);

        if (!collectionOptional.isPresent()) {
            throw new CollectionNotFoundException(collectionId);
        }
        if(!bookOptional.isPresent()){
            throw new BookNotFoundException(bookId);
        }
    
        BookCollection collection = collectionOptional.get();
        Book book = bookOptional.get();
        
        collection.removeBook(book);
        
        return bCRepository.save(collection);
    }

    public BookCollection getCollectionById(Long collectionId) {
        BookCollection collection = bCRepository.findById(collectionId).orElseThrow(() -> new CollectionNotFoundException(collectionId));
        return collection;
    }

    
}
