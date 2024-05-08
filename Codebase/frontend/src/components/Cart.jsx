import React, { useState, useEffect, useContext } from 'react';
import { useParams, Link, useNavigate } from "react-router-dom";
import { UserContext } from "./UserContext";

const Cart = () => {
  const [cart, setCart] = useState(null);
  const { user, fetchUserData } = useContext(UserContext);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCart = async () => {
      try {
        const response = await fetch(`http://localhost:8080/cart/get/${user.id}`);
        const data = await response.json();
        setCart(data);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching cart:', error);
        setLoading(false);
      }
    };

    fetchCart();
  }, [user.id]);

  const removeItemFromCart = async (cartId, cartItemId) => {
    try {
      const response = await fetch(`http://localhost:8080/cart/remove-item/${cartId}/${cartItemId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        }
      });
      if (!response.ok) {
        throw new Error('Failed to remove item from cart');
      }
      const data = await response.json();
      setCart(data);
    } catch (error) {
      console.error('Error removing item from cart:', error);
    }
  };

  if (loading) return <p>Loading...</p>;
  if (!cart) return <p>Cart not found</p>;

  return (
    <div>
      <h2>Cart</h2>
      <ul>
        {cart.items.map(item => (
          <li key={item.id}>
            {item.book.title}
            <button onClick={() => removeItemFromCart(cart.cartId, item.cartItemId)}>Remove</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Cart;
