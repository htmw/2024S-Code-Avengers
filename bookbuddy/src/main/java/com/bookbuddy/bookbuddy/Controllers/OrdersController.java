

package com.bookbuddy.bookbuddy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookbuddy.bookbuddy.Entities.Cart;
import com.bookbuddy.bookbuddy.Entities.CartItem;
import com.bookbuddy.bookbuddy.Entities.OrderItem;
import com.bookbuddy.bookbuddy.Entities.Orders;
import com.bookbuddy.bookbuddy.ServiceClasses.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	   @Autowired
    private final OrdersService ordersService ;
    
	public OrdersController( OrdersService ordersService) {
		this.ordersService = ordersService;
	}
	
	 @PostMapping("/save-order")
	    public String newUser(@RequestBody Orders order) {
	        return ordersService.saveOrder(order);
	    }

	 @PostMapping("/save-order-item")
	    public String newUser(@RequestBody OrderItem order) {
		 System.out.println(order.toString());
	        return ordersService.saveOrderItem(order);
	    }
	 
	   @GetMapping("fetch-order/{orderId}")
	    public Orders findUser(@PathVariable Long orderId) {
	        return ordersService.fetchOrders(orderId);
	    }


	
	
	

}
