package com.bookbuddy.bookbuddy.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="author")   
    private String author;
    @Column(name="price")
    private float price;
    @Column(name="isbn")
    private String isbn;
    @Column(name="publication_date")
    private String publicationDate;
    @Column(name="genre")
    private String genre;
    @Column(name="description")
    private String description;
    
////    @OneToMany(mappedBy = "book"  , cascade= CascadeType.ALL)
//    private OrderItem orderitem;
//    
////    @OneToMany(mappedBy = "book"  , cascade= CascadeType.ALL)
//    private CartItem cartItem;

//	public OrderItem getOrderitem() {
//		return orderitem;
//	}
//
//	public void setOrderitem(OrderItem orderitem) {
//		this.orderitem = orderitem;
//	}
//
//	public CartItem getCartItem() {
//		return cartItem;
//	}
//
//	public void setCartItem(CartItem cartItem) {
//		this.cartItem = cartItem;
//	}

	public float getPrice() {
    	return price;
    }
    
    public void setPrice(float price) {
    	 this.price = price;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
