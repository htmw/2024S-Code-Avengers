

package com.bookbuddy.bookbuddy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.Entities.Order;
import com.bookbuddy.bookbuddy.Entities.OrderItem;
import com.bookbuddy.bookbuddy.ServiceClasses.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	   @Autowired
    private final OrderService orderService ;
    
	public OrderController( OrderService orderService) {
		this.orderService = orderService;
	}
	
	 @PostMapping("/save-order")
	    public String newUser(@RequestBody Order order) {
	        return orderService.saveOrder(order);
	    }

	 @PostMapping("/save-order-item")
	    public String newUser(@RequestBody OrderItem order) {
		 System.out.println(order.toString());
	        return orderService.saveOrderItem(order);
	    }
	 
	   @GetMapping("fetch-order/{orderId}")
	    public Order findUser(@PathVariable Long orderId) {
	        return orderService.fetchOrders(orderId);
	    }


	
	
	

}
