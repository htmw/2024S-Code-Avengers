package com.bookbuddy.bookbuddy.Entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shopping_cart")
public class Cart {
	
	@Id
    @Column(name="cart_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;
	
	    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

	
	 @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	 private List<CartItem> cartItems = new ArrayList<>();

	
	@Column(name = "total_price")
	private BigDecimal totalPrice;


	public Long getCartId() {
		return cartId;
	}


	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<CartItem> getCartItems() {
		return cartItems;
	}


	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}


	public BigDecimal getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}


	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", user=" + user + ", cartItems=" + cartItems + ", totalPrice=" + totalPrice
				+ "]";
	}


	
}
