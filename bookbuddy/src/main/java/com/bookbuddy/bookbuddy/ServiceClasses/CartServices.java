package com.bookbuddy.bookbuddy.ServiceClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.Entities.Cart;
import com.bookbuddy.bookbuddy.Entities.CartItem;
import com.bookbuddy.bookbuddy.Repository.CartItemRepository;
import com.bookbuddy.bookbuddy.Repository.CartRepository;
import com.bookbuddy.bookbuddy.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServices {
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private UserRepository userRepository;

	public CartServices(CartItemRepository cartItemRepository, CartRepository cartRepository,
			UserRepository userRepository) {
		this.cartItemRepository = cartItemRepository;
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;

	}

	public String saveCartItem(CartItem item) {
		String response;
		try {
			// need to update book price by using book service
			item.setItemPrice(item.getQuantity() * item.getBook().getPrice());
			System.out.println(item.toString());
			cartItemRepository.save(item);
			response = "Cart Item saved Successfully";
		} catch (Exception e) {
			System.out.print("Exception occured " + e.getMessage());
			response = "Exception occured " + e.getMessage();
		}
		return response;
	}

	public String saveCart(Cart item) {
		String response;
		try {
			System.out.println(item);
			cartRepository.save(item);
			response = "Cart saved Successfully";
		} catch (Exception e) {
			System.out.print("Exception occured " + e.getMessage());
			response = "Exception occured " + e.getMessage();
		}
		return response;
	}

	public List<CartItem> fetchCartItems(long cartId) {
		List<CartItem> cartItems = new ArrayList<CartItem>();

		try {
			cartItems = cartItemRepository.findByCart_CartId(cartId);
		} catch (Exception e) {
			System.out.print("Exception occured " + e.getMessage());
		}
		return cartItems;
	}

	public Cart validateCart(long cartId) {
		Cart c = new Cart();

		try {
			c = cartRepository.findById(cartId);
			if (c != null) {
				return c;
			} else {
				throw new Exception("Invalid Cart Id");
			}
		} catch (Exception e) {
			System.out.print("Exception occured " + e.getMessage());
		}
		return c;
	}

	public Cart fetchCart(long cartId) {

		Cart c = new Cart();

		try {
			c = validateCart(cartId);

			if (c != null) {
				c.setUser(null);
				System.out.println("cart is not null");
				List<CartItem> cartItems = fetchCartItems(cartId);
				if (cartItems.size() > 0) {
		
					double totalPrice = 1.0;
					for (CartItem item : cartItems) {
						item.setCart(null);
						totalPrice = totalPrice + item.getQuantity() * item.getBook().getPrice();
						
					}
					c.setTotalPrice(totalPrice);
					c.setCartItems(cartItems);

				}
			} else {
				return c;
			}

		} catch (Exception e) {
			System.out.print("Exception occured " + e.getMessage());
		}
		return c;

	}
}