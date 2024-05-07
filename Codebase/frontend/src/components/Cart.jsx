import React, { useState, useEffect, useContext } from "react";
import { UserContext } from "./UserContext";

const Cart = () => {
  const [cart, setCart] = useState({
    id: "",
    userId: "",
    items: [],
    totalPrice: 0,
  });
  const { user } = useContext(UserContext);

  useEffect(() => {
    fetchCart();
  }, []);

  const fetchCart = async () => {
    try {
      const response = await fetch(`http://localhost:8080/cart/get/${user.id}`);
      if (response.ok) {
        const data = await response.json();
        setCart(data);
      } else {
        console.error("Error fetching cart:", response.statusText);
      }
    } catch (error) {
      console.error("Error fetching cart:", error);
    }
  };

  const handleRemoveFromCart = async (cartId, cartItemId) => {
    try {
      const response = await fetch(
        `http://localhost:8080/cart/remove-item/${cartId}/${cartItemId}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
        },
      );
      if (response.ok) {
        fetchCart();
      } else {
        console.error("Error removing item from cart:", response.statusText);
      }
    } catch (error) {
      console.error("Error removing item from cart:", error);
    }
  };

  return (
    <div className="cart-page">
      <h1>Your Cart</h1>
      {cart.map((item) => (
        <div key={item.id}>
          <p>{item.name}</p>
          <p>Quantity: {item.quantity}</p>
          <button onClick={() => handleRemoveFromCart(cart.id, item.id)}>
            Remove from cart
          </button>
        </div>
      ))}
    </div>
  );
};

export default Cart;
