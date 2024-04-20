package com.bookbuddy.bookbuddy.ServiceClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Book;
import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Entities.UserDTO;
import com.bookbuddy.bookbuddy.Repository.BookCollectionRepository;
import com.bookbuddy.bookbuddy.Repository.BookRepository;
import com.bookbuddy.bookbuddy.Repository.UserRepository;

@Service
public class UserService {
    private final UserRepository uRepository;
    private final BookCollectionRepository bCRepository;
    private final BookRepository bRepository;

    public UserService(UserRepository repository, BookCollectionRepository bCRepository, BookRepository bRepository){
        this.uRepository = repository;
        this.bCRepository = bCRepository;
        this.bRepository = bRepository;
    }
    
    public BookCollection getAndSaveRecommendations(User user){
        //Call to ML API to get Recommendations
        List<String> mlRecommendations = new ArrayList<>();
        //Store Recommendations
        List<Book> recommendations = new ArrayList<>();
        for(String isbn : mlRecommendations){
            Book book = bRepository.findByIsbn(isbn);
            if(book != null){
                recommendations.add(book);
            }
        }
        BookCollection books = new BookCollection(user, "Recommendations");
        books.setBooks(recommendations);
        return bCRepository.save(books);
    }

    public UserDTO getUserDetails(Long userId){
        User user = uRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        
        return new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getDateOfBirth());
    }

    public User updateUser(Long userId, User updatedUser){
        Optional<User> userOptional = uRepository.findById(userId);
        if(!userOptional.isPresent()){
            throw new UserNotFoundException(userId);
        }
        User existingUser = userOptional.get();

        if(updatedUser.getEmail() != null) existingUser.setEmail(updatedUser.getEmail());
        if(updatedUser.getFirstName() != null) existingUser.setFirstName(updatedUser.getFirstName());
        if(updatedUser.getLastName() != null) existingUser.setLastName(updatedUser.getLastName());
        if(updatedUser.getDateOfBirth() != null) existingUser.setDateOfBirth(updatedUser.getDateOfBirth());

        return uRepository.save(existingUser);
    }

    public User addNewUser(User newUserDetails) {
        User user = new User(newUserDetails.getFirebaseUID(), newUserDetails.getFirstName(), newUserDetails.getLastName(), newUserDetails.getEmail(), newUserDetails.getDateOfBirth());
        return uRepository.save(user);
    }


}