package com.bookbuddy.bookbuddy.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem {

	@Id
	@Column(name = "cart_item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartItemId;

	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "item_price")
	private double itemPrice;
	

	public Long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public CartItem(Long cartItemId, Cart cart, Book book, int quantity, double itemPrice) {
		super();
		this.cartItemId = cartItemId;
		this.cart = cart;
		this.book = book;
		this.quantity = quantity;
		this.itemPrice = itemPrice;
	}

	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	


}