package com.bookbuddy.bookbuddy.Entities;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

//This is the CartDTO class. It is a Data Transfer Object (DTO) that is used to send cart information back to the client.
//It has the cart ID, user ID, list of items in the cart, and the total price of all items in the cart.
//This main difference between this class and the Cart class is that this class does not have a reference to the User entity, only the user ID.
//Jimmy Karoly

@Schema(name = "CartDTO", description = "Data Transfer Object for Cart")
public class CartDTO {

    @Schema(name = "cartId", description = "Unique ID corresponding to a Cart", example = "1")
    private Long cartId;

    @Schema(name = "userId", description = "Unique ID corresponding to a User", example = "1")
    private Long userId;

    @Schema(name = "items", description = "List of items in the cart")
    private List<CartItem> items;

    @Schema(name = "totalPrice", description = "Total price of all items in the cart", example = "100.00")
    private BigDecimal totalPrice;

    public static CartDTO fromEntity(Cart cart){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setUserId(cart.getUser().getId());
        cartDTO.setItems(cart.getCartItems());
        cartDTO.setTotalPrice(cart.getTotalPrice());
        return cartDTO;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
}
