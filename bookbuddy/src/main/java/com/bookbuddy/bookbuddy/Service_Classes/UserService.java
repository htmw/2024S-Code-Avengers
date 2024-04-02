package com.bookbuddy.bookbuddy.Service_Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.Controllers_Repositories.BookCollectionRepository;
import com.bookbuddy.bookbuddy.Controllers_Repositories.BookRepository;
import com.bookbuddy.bookbuddy.Controllers_Repositories.UserRepository;
import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Book;
import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.Entities.User;

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

    public HashMap<String, Object> getUserDetails(Long userId){
        User user = uRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        HashMap<String, Object> userDetails = new HashMap<>();

        userDetails.put("firstName", user.getFirstName());
        userDetails.put("lastName", user.getLastName());
        userDetails.put("email", user.getEmail());
        userDetails.put("dateOfBirth", user.getDateOfBirth());

        return userDetails;
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

    public User addNewUser(Long firebaseUID, String firstName, String lastName, String email, String dateOfBirth){
        User user = new User(firebaseUID, firstName, lastName, email, dateOfBirth);
        return uRepository.save(user);
    }


}
