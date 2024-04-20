package com.bookbuddy.bookbuddy.ServiceClasses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.Entities.Cart;
import com.bookbuddy.bookbuddy.Entities.CartItem;
import com.bookbuddy.bookbuddy.Repository.CartItemRepository;
import com.bookbuddy.bookbuddy.Repository.CartRepository;
import com.bookbuddy.bookbuddy.Repository.UserRepository;

@Service
public class CartServices {
	@Autowired
	private final CartItemRepository cartItemRepository;
	@Autowired
	private final CartRepository cartRepository;
	@Autowired
	private final UserRepository userRepository;

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
			item.setItemPrice(item.getBook().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
			System.out.println(item.toString());
			cartItemRepository.save(item);
			response = "Cart Item saved Successfully";
		} catch (Exception e) {
			System.out.print("Exception occurred " + e.getMessage());
			response = "Exception occurred " + e.getMessage();
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
			System.out.print("Exception occurred " + e.getMessage());
			response = "Exception occurred " + e.getMessage();
		}
		return response;
	}

	public List<CartItem> fetchCartItems(long cartId) {
		List<CartItem> cartItems = new ArrayList<>();

		try {
			cartItems = cartItemRepository.findByCart_CartId(cartId);
		} catch (Exception e) {
			System.out.print("Exception occurred " + e.getMessage());
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
			System.out.print("Exception occurred " + e.getMessage());
		}
		return c;
	}

	public Cart fetchCart(long cartId) {

		Cart c = new Cart();

		try {
			c = validateCart(cartId);

			if (c != null) {
				// c.setUser(null);
				System.out.println("cart is not null");
				List<CartItem> cartItems = fetchCartItems(cartId);
				if (!cartItems.isEmpty()) {
		
					BigDecimal totalPrice = BigDecimal.ONE;
					for (CartItem item : cartItems) {
						item.setCart(null);
						totalPrice = totalPrice.add(item.getBook().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
						
					}
					c.setTotalPrice(totalPrice);
					c.setCartItems(cartItems);

				}
			} else {
				return c;
			}

		} catch (Exception e) {
			System.out.print("Exception occurred " + e.getMessage());
		}
		return c;

	}
}