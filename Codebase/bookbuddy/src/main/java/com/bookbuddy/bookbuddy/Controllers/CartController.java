package com.bookbuddy.bookbuddy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.Entities.CartDTO;
import com.bookbuddy.bookbuddy.ServiceClasses.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="Cart Controller", description="Endpoint for a User's cart")
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    CartService cartService;
	
	@PostMapping("/add-item/{cartId}/{bookId}")
	@Operation(summary="Add an item to a cart")
	public ResponseEntity<CartDTO> addItemToCart(
		@Parameter(description="Unique ID corresponding to a Cart", example="001") @PathVariable Long cartId,
		@Parameter(description="Unique ID corresponding to a Book", example="001") @PathVariable long bookId)
	{
		CartDTO updatedCart = cartService.addItemToCart(cartId, bookId);
		return ResponseEntity.ok(updatedCart);
	}

	@PutMapping("/remove-item/{cartId}/{cartItemId}")
	@Operation(summary="Remove an item from a cart")
	public ResponseEntity<CartDTO> removeItemFromCart(
		@Parameter(description="Unique ID corresponding to a Cart", example="1") @PathVariable Long cartId, 
		@Parameter(description="Unique ID corresponding to a CartItem", example="1") @PathVariable Long cartItemId) 
	{
		CartDTO updatedCart = cartService.removeItemFromCart(cartId, cartItemId);
		return ResponseEntity.ok(updatedCart);
	}
	 
	@GetMapping("get/{userId}")
	@Operation(summary="Get a cart by ID")
	public ResponseEntity<CartDTO> getCart(
		@Parameter(description="Unique ID corresponding to a User", example="001") @PathVariable Long userId) 
	{
		CartDTO cart = cartService.getCart(userId);
		return ResponseEntity.ok(cart);
	}


}
