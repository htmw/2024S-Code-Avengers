

package com.bookbuddy.bookbuddy.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.Entities.OrderDTO;
import com.bookbuddy.bookbuddy.ServiceClasses.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="Order Controller", description="Endpoints for Orders")
@RequestMapping("/orders")
public class OrderController {

	@Autowired
    OrderService orderService ;
	
	@PostMapping("/create/{cartId}")
	@Operation(summary="Create an order from a cart")
	public ResponseEntity<OrderDTO> saveOrder(
		@Parameter(description="Unique ID corresponding to a Cart", example="1") @PathVariable Long cartId) 
	{
		OrderDTO order = orderService.createOrder(cartId);
		return ResponseEntity.ok(order);
	}

	@GetMapping("/user-orders/{userId}")
	@Operation(summary="Get all orders for a user")
	public ResponseEntity<List<OrderDTO>> findOrdersForUser(
		@Parameter(description="Unique ID corresponding to a User", example="1") @PathVariable Long userId) 
	{
		List<OrderDTO> orders = orderService.getOrdersForUser(userId);
		return ResponseEntity.ok(orders);
	}
	 
	@GetMapping("/{orderId}")
	@Operation(summary="Get order details by ID")
	public ResponseEntity<OrderDTO> findOrder(
		@Parameter(description="Unique ID corresponding to a Cart", example="1") @PathVariable Long orderId) 
	{
		OrderDTO order = orderService.getOrder(orderId);
		return ResponseEntity.ok(order);
	}


	
	
	

}
