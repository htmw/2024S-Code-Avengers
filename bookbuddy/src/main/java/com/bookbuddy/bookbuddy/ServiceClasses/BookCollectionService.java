package com.bookbuddy.bookbuddy.ServiceClasses;

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
    private final BookRepository bookRepository;
    @Autowired
    private final BookCollectionRepository bookCollectionRepository;
    @Autowired
    private final UserRepository userRepository;

    public BookCollectionService(UserRepository uRepository, BookRepository bRepository, BookCollectionRepository bCRepository){
        this.bookRepository = bRepository;
        this.userRepository = uRepository;
        this.bookCollectionRepository = bCRepository;
    }

    public void deleteCollection(Long collectionId){
        bookCollectionRepository.deleteById(collectionId);
    }

    public BookCollection createCollection(Long userId, String name){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        BookCollection newCollection = new BookCollection(user, name);
        return bookCollectionRepository.save(newCollection);

    }

    public BookCollection renameCollection(Long collectionId, String newName) {
        BookCollection bookCollection = bookCollectionRepository.findById(collectionId).orElseThrow(() -> new CollectionNotFoundException(collectionId));
    
        bookCollection.setCollectionName(newName);
        return bookCollectionRepository.save(bookCollection);
    }

    public BookCollection addBook(Long collectionId, Long bookId){
        BookCollection bookCollection = bookCollectionRepository.findById(collectionId).orElseThrow(() -> new CollectionNotFoundException(collectionId));
        Book bookToAdd = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));

        bookCollection.addBook(bookToAdd);
        
        return bookCollectionRepository.save(bookCollection);
    }

    public BookCollection removeBook(Long collectionId, Long bookId){
        BookCollection bookCollection = bookCollectionRepository.findById(collectionId).orElseThrow(() -> new CollectionNotFoundException(collectionId));
        Book bookToRemove = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        
        bookCollection.removeBook(bookToRemove);
        
        return bookCollectionRepository.save(bookCollection);
    }

    public BookCollection getCollectionById(Long collectionId) {
        BookCollection collection = bookCollectionRepository.findById(collectionId).orElseThrow(() -> new CollectionNotFoundException(collectionId));
        return collection;
    }

    
}
