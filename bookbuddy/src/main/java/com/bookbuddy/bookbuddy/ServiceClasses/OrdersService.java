

package com.bookbuddy.bookbuddy.ServiceClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.bookbuddy.bookbuddy.Entities.OrderItem;
import com.bookbuddy.bookbuddy.Entities.Orders;
import com.bookbuddy.bookbuddy.Repository.OrderItemRepository;
import com.bookbuddy.bookbuddy.Repository.OrdersRepository;
import com.bookbuddy.bookbuddy.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {
	   @Autowired
	private OrderItemRepository orderItemRepository;
	   @Autowired
	private OrdersRepository orderRepository;
	   @Autowired
	private UserRepository userRepository;
	

	public OrdersService(OrderItemRepository orderItemRepository, OrdersRepository orderRepository,
			UserRepository userRepository) {
		this.orderItemRepository = orderItemRepository;
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;

	}

	public String saveOrderItem(OrderItem item) {
		String response;
		try {
			orderItemRepository.save(item);
			response = "Order Item saved Successfully";
		} catch (Exception e) {
			System.out.print("Exception occured " + e.getMessage());
			response = "Exception occured " + e.getMessage();
		}
		return response;
	}

	public String saveOrder(Orders order) {
		String response;
		try {
			orderRepository.save(order);
			response = "Order saved Successfully";
		} catch (Exception e) {
			System.out.print("Exception occured " + e.getMessage());
			response = "Exception occured " + e.getMessage();
		}
		return response;
	}

	public List<OrderItem> fetchOrderItems(long orderId) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();

		try {
			orderItems = orderItemRepository.findByOrder_OrderId(orderId);
		} catch (Exception e) {
			System.out.print("Exception occured " + e.getMessage());
		}
		return orderItems;
	}

	public Orders validateOrder(long orderId) {
		Orders c = new Orders();

		try {
			c = orderRepository.findById(orderId);
			if (c != null) {
				return c;
			} else {
				throw new Exception("Invalid Order Id");
			}
		} catch (Exception e) {
			System.out.print("Exception occured " + e.getMessage());
		}
		return c;
	}

	public Orders fetchOrders(long orderId) {

		Orders c = new Orders();

		try {
			c = validateOrder(orderId);

			if (c != null) {
				c.setUser(null);
				List<OrderItem> orderItems = fetchOrderItems(orderId);
				if (orderItems.size() > 0) {
					c.setOrderItems(orderItems);
					double totalPrice = 0.0;
					for (OrderItem item : orderItems) {
						item.setOrders(null);
						totalPrice = totalPrice + item.getQuantity() * item.getBook().getPrice();

					}
					c.setTotalPrice(totalPrice);
					c.setOrderItems(orderItems);

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