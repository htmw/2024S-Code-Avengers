package com.bookbuddy.bookbuddy.ServiceClasses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.CreatedExceptions.BookNotFoundException;
import com.bookbuddy.bookbuddy.CreatedExceptions.CollectionNotFoundException;
import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Book;
import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.Entities.BookCollectionDTO;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Repository.BookCollectionRepository;
import com.bookbuddy.bookbuddy.Repository.BookRepository;
import com.bookbuddy.bookbuddy.Repository.UserRepository;

// This is the Book Collection Service. It is a service class that handles the business logic for book collections.
// It has methods for creating a collection, renaming a collection, adding and removing a book from a collection, and deleting a collection.
// Jimmy Karoly

@Service
public class BookCollectionService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookCollectionRepository bookCollectionRepository;
    @Autowired
    UserRepository userRepository;

    public void deleteCollection(Long collectionId){
        bookCollectionRepository.deleteById(collectionId);
    }

    public BookCollectionDTO createCollection(Long userId, String name){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        BookCollection newCollection = new BookCollection(user, name);
        bookCollectionRepository.save(newCollection);
        return BookCollectionDTO.fromEntity(newCollection);

    }

    public BookCollectionDTO renameCollection(Long collectionId, String newName) {
        BookCollection bookCollection = bookCollectionRepository.findById(collectionId).orElseThrow(() -> new CollectionNotFoundException(collectionId));
    
        bookCollection.setCollectionName(newName);
        bookCollectionRepository.save(bookCollection);

        return BookCollectionDTO.fromEntity(bookCollection);
    }

    public BookCollectionDTO addBook(Long userId, String collectionName, Long bookId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        
        BookCollection bookCollection = bookCollectionRepository.findByUserAndCollectionName(user, collectionName);
    
        Book bookToAdd = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
    

        bookCollection.addBook(bookToAdd);
        bookCollectionRepository.save(bookCollection);
    
        return BookCollectionDTO.fromEntity(bookCollection);
    }
    

    public BookCollectionDTO removeBook(Long collectionId, Long bookId){
        BookCollection bookCollection = bookCollectionRepository.findById(collectionId).orElseThrow(() -> new CollectionNotFoundException(collectionId));
        Book bookToRemove = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        
        bookCollection.removeBook(bookToRemove);
        bookCollectionRepository.save(bookCollection);

        return BookCollectionDTO.fromEntity(bookCollection);
    }

    public BookCollectionDTO getCollectionById(Long collectionId) {
        BookCollection collection = bookCollectionRepository.findById(collectionId).orElseThrow(() -> new CollectionNotFoundException(collectionId));
        return BookCollectionDTO.fromEntity(collection);
    }

    public List<BookCollectionDTO> getAllFromUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow( () -> new UserNotFoundException(userId));
        List<BookCollection> collections = user.getBookCollections();
        List<BookCollectionDTO> collectionDTOs = new ArrayList<>();
        for(BookCollection collection : collections){
            collectionDTOs.add(BookCollectionDTO.fromEntity(collection));
        }
        return collectionDTOs;
    }

}
