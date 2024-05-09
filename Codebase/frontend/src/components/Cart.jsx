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

  const handleCheckout = () => {
    navigate('/usercheckout', { state: { cart } });
  };

  if (loading) return <p>Loading...</p>;
  if (!cart) return <p>Cart not found</p>;

  return (
    <div className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
      <h2 className="text-2xl font-bold mb-4">Cart</h2>
      <ul>
        {cart.items.map(item => (
          <li key={item.id} className="flex items-center justify-between border-b-2 border-gray-200 py-2">
            <span className="text-lg">{item.book.title}</span>
            <button onClick={() => removeItemFromCart(cart.cartId, item.cartItemId)} className="text-sm text-red-500 hover:text-red-700">
              Remove
            </button>
          </li>
        ))}
      </ul>
      <button
        onClick={handleCheckout}
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4"
      >
        Proceed to Checkout
      </button>
    </div>
  );
};

export default Cart;