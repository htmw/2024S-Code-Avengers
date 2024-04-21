package com.bookbuddy.bookbuddy.ServiceClasses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.CreatedExceptions.BookNotFoundException;
import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Book;
import com.bookbuddy.bookbuddy.Entities.BookCollection;
import com.bookbuddy.bookbuddy.Entities.Cart;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Entities.UserDTO;
import com.bookbuddy.bookbuddy.Repository.BookCollectionRepository;
import com.bookbuddy.bookbuddy.Repository.BookRepository;
import com.bookbuddy.bookbuddy.Repository.CartRepository;
import com.bookbuddy.bookbuddy.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BookCollectionRepository bookCollectionRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final CartRepository cartRepository;

    public UserService(UserRepository uRepository, BookCollectionRepository bCRepository, BookRepository bRepository, CartRepository cartRepository){
        this.userRepository = uRepository;
        this.bookCollectionRepository = bCRepository;
        this.bookRepository = bRepository;
        this.cartRepository = cartRepository;
    }
    
    public BookCollection getAndSaveRecommendations(User user){
        //Call to ML API to get Recommendations
        List<Long> mlRecommendations = new ArrayList<>();
        //Store Recommendations
        List<Book> recommendations = new ArrayList<>();
        for(Long id : mlRecommendations){
            Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
            if(book != null){
                recommendations.add(book);
            }
        }
        BookCollection books = new BookCollection(user, "Recommendations");
        books.setBooks(recommendations);
        return bookCollectionRepository.save(books);
    }

    public UserDTO getUserDetails(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        
        return new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getDateOfBirth());
    }

    public User updateUser(Long userId, User updatedUser){
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if(updatedUser.getEmail() != null) existingUser.setEmail(updatedUser.getEmail());
        if(updatedUser.getFirstName() != null) existingUser.setFirstName(updatedUser.getFirstName());
        if(updatedUser.getLastName() != null) existingUser.setLastName(updatedUser.getLastName());
        if(updatedUser.getDateOfBirth() != null) existingUser.setDateOfBirth(updatedUser.getDateOfBirth());

        return userRepository.save(existingUser);
    }

    public User addNewUser(User newUserDetails) {
        User user = new User(newUserDetails.getFirebaseUID(), newUserDetails.getFirstName(), newUserDetails.getLastName(), newUserDetails.getEmail(), newUserDetails.getDateOfBirth());
        userRepository.save(user);
        Cart newCart = new Cart();
		newCart.setUser(user);
		cartRepository.save(newCart);
        return user;
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }


}
