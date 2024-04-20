

package com.bookbuddy.bookbuddy.ServiceClasses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.Entities.Order;
import com.bookbuddy.bookbuddy.Entities.OrderItem;
import com.bookbuddy.bookbuddy.Repository.OrderItemRepository;
import com.bookbuddy.bookbuddy.Repository.OrdersRepository;
import com.bookbuddy.bookbuddy.Repository.UserRepository;

@Service
public class OrderService {
	   @Autowired
	private OrderItemRepository orderItemRepository;
	   @Autowired
	private OrdersRepository orderRepository;
	   @Autowired
	private UserRepository userRepository;
	

	public OrderService(OrderItemRepository orderItemRepository, OrdersRepository orderRepository,
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
			System.out.print("Exception occurred " + e.getMessage());
			response = "Exception occurred " + e.getMessage();
		}
		return response;
	}

	public String saveOrder(Order order) {
		String response;
		try {
			orderRepository.save(order);
			response = "Order saved Successfully";
		} catch (Exception e) {
			System.out.print("Exception occurred " + e.getMessage());
			response = "Exception occurred " + e.getMessage();
		}
		return response;
	}

	public List<OrderItem> fetchOrderItems(long orderId) {
		List<OrderItem> orderItems = new ArrayList<>();

		try {
			orderItems = orderItemRepository.findByOrder_OrderId(orderId);
		} catch (Exception e) {
			System.out.print("Exception occurred " + e.getMessage());
		}
		return orderItems;
	}

	public Order validateOrder(long orderId) {
		Order c = new Order();

		try {
			c = orderRepository.findById(orderId);
			if (c != null) {
				return c;
			} else {
				throw new Exception("Invalid Order Id");
			}
		} catch (Exception e) {
			System.out.print("Exception occurred " + e.getMessage());
		}
		return c;
	}

	public Order fetchOrders(long orderId) {

		Order c = new Order();

		try {
			c = validateOrder(orderId);

			if (c != null) {
				c.setUser(null);
				List<OrderItem> orderItems = fetchOrderItems(orderId);
				if (!orderItems.isEmpty()) {
					c.setOrderItems(orderItems);
					double totalPrice = 0.0;
					for (OrderItem item : orderItems) {
						item.setOrder(null);
						totalPrice = totalPrice + item.getQuantity() * item.getBook().getPrice();

					}
					c.setTotalPrice(totalPrice);
					c.setOrderItems(orderItems);

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