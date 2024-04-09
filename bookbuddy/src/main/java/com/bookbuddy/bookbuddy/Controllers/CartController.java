package com.bookbuddy.bookbuddy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.Entities.Cart;
import com.bookbuddy.bookbuddy.Entities.CartItem;


import com.bookbuddy.bookbuddy.ServiceClasses.CartServices;

@RestController
@RequestMapping("/cart")
public class CartController {

   @Autowired
    private final CartServices cartService;
    
	public CartController( CartServices cartService) {
		this.cartService = cartService;
	}
	
	@RequestMapping(value = "/save-cart", method = RequestMethod.POST)
	    public String newUser(@RequestBody Cart cart) {
		System.out.println(cart.toString());
	        return cartService.saveCart(cart);
	    }

	 @PostMapping("/save-cart-item")
	    public String newUser(@RequestBody CartItem cart) {
	        return cartService.saveCartItem(cart);
	    }
	 
	   @GetMapping("fetch-cart/{cartId}")
	    public Cart findUser(@PathVariable Long cartId) {
	        return cartService.fetchCart(cartId);
	    }


}
