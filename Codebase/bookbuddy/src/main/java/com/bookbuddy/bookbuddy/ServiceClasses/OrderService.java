package com.bookbuddy.bookbuddy.ServiceClasses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.CreatedExceptions.CartNotFoundException;
import com.bookbuddy.bookbuddy.CreatedExceptions.OrderNotFoundException;
import com.bookbuddy.bookbuddy.CreatedExceptions.PaymentFailedException;
import com.bookbuddy.bookbuddy.CreatedExceptions.UserNotFoundException;
import com.bookbuddy.bookbuddy.Entities.Cart;
import com.bookbuddy.bookbuddy.Entities.CartItem;
import com.bookbuddy.bookbuddy.Entities.Order;
import com.bookbuddy.bookbuddy.Entities.OrderDTO;
import com.bookbuddy.bookbuddy.Entities.OrderItem;
import com.bookbuddy.bookbuddy.Entities.PaymentResult;
import com.bookbuddy.bookbuddy.Entities.User;
import com.bookbuddy.bookbuddy.Repository.CartRepository;
import com.bookbuddy.bookbuddy.Repository.OrderRepository;
import com.bookbuddy.bookbuddy.Repository.UserRepository;

@Service
public class OrderService {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	UserRepository userRepository;
    @Autowired
    StripeService stripeService;
	

	public OrderDTO getOrder(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		return OrderDTO.fromEntity(order);
	}

	public List<OrderDTO> getOrdersForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<Order> orders = user.getOrders();
        List<OrderDTO> ordersDTOs = new ArrayList<>();
        for(Order order : orders){
            ordersDTOs.add(OrderDTO.fromEntity(order));
        }
        return ordersDTOs;
    }

	public OrderDTO createOrder(Long cartId, String paymentToken) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
		
		Order newOrder = new Order();
        newOrder.setUser(cart.getUser());
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> items = new ArrayList<>();
        for(CartItem item : cart.getCartItems()){
            OrderItem newItem = new OrderItem(newOrder, item.getBook(), item.getItemPrice());
            items.add(newItem);
            totalPrice = totalPrice.add(newItem.getItemPrice());
        }
        newOrder.setOrderItems(items);
        newOrder.setTotalPrice(totalPrice);
        PaymentResult paymentResult = stripeService.processPayment(newOrder.getTotalPrice(), paymentToken);

        if(paymentResult.isSuccessful()){
            orderRepository.save(newOrder);
            cart.setCartItems(new ArrayList<>());
            cart.setTotalPrice(BigDecimal.ZERO);
            cartRepository.save(cart);

            return OrderDTO.fromEntity(newOrder);
        }
        else{
            throw new PaymentFailedException(paymentResult.getErrorMessage());
        } 
	}

}