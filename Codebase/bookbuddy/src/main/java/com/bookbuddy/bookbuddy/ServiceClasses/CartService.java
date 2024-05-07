package com.bookbuddy.bookbuddy.ServiceClasses;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.CreatedExceptions.BookNotFoundException;
import com.bookbuddy.bookbuddy.CreatedExceptions.CartItemNotFoundException;
import com.bookbuddy.bookbuddy.CreatedExceptions.CartNotFoundException;
import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Book;
import com.bookbuddy.bookbuddy.Entities.Cart;
import com.bookbuddy.bookbuddy.Entities.CartDTO;
import com.bookbuddy.bookbuddy.Entities.CartItem;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Repository.BookRepository;
import com.bookbuddy.bookbuddy.Repository.CartItemRepository;
import com.bookbuddy.bookbuddy.Repository.CartRepository;
import com.bookbuddy.bookbuddy.Repository.UserRepository;

@Service
public class CartService {
    
    @Autowired
    CartRepository cartRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CartItemRepository cartItemRepository;
	@Autowired
	UserRepository userRepository;


    public CartDTO addItemToCart(Long cartId, Long bookId, int quantity) {
        Book bookToAdd = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        Cart userCart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));

        CartItem itemToAdd = new CartItem(userCart, bookToAdd, quantity, bookToAdd.getPrice());
        userCart.getCartItems().add(itemToAdd);
        userCart.setTotalPrice(calculateTotalPrice(userCart));
        cartItemRepository.save(itemToAdd);
        cartRepository.save(userCart);
        return CartDTO.fromEntity(userCart);
    }
    
	public CartDTO removeItemFromCart(Long cartId, Long cartItemId) {
		Cart cartToUpdate = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));

		CartItem itemToRemove = cartToUpdate.getCartItems().stream()
		.filter(item -> item.getCartItemId().equals(cartItemId))
		.findFirst()
		.orElseThrow(() -> new CartItemNotFoundException(cartItemId));

		cartToUpdate.getCartItems().remove(itemToRemove);
		BigDecimal totalPrice = calculateTotalPrice(cartToUpdate);
		cartToUpdate.setTotalPrice(totalPrice);

		cartRepository.save(cartToUpdate);
		return CartDTO.fromEntity(cartToUpdate);
	}

	public CartDTO updateCartItemQuantity(Long cartId, Long cartItemId, int newQuantity) {
		Cart cartToUpdate = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));

		CartItem itemToUpdate = cartToUpdate.getCartItems().stream()
		.filter(item -> item.getCartItemId().equals(cartItemId))
		.findFirst()
		.orElseThrow(() -> new CartItemNotFoundException(cartItemId));

		itemToUpdate.setQuantity(newQuantity);
		BigDecimal totalItemPrice = calculateTotalPrice(cartToUpdate);
		cartToUpdate.setTotalPrice(totalItemPrice);

		cartRepository.save(cartToUpdate);

		return CartDTO.fromEntity(cartToUpdate);
	}

    private BigDecimal calculateTotalPrice(Cart cart) {
		BigDecimal totalPrice = BigDecimal.ZERO;

		for(CartItem item : cart.getCartItems()){
			BigDecimal itemPrice = item.getBook().getPrice().multiply(BigDecimal.valueOf((double)item.getQuantity()));
			totalPrice = totalPrice.add(itemPrice);
		}
		return totalPrice;
	}

    public CartDTO getCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		Cart userCart = user.getCart();
        return CartDTO.fromEntity(userCart);
    }


}