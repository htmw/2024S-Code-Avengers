import React, { useState, useEffect, useContext } from 'react';
import { UserContext } from './UserContext';

const UserCheckout = () => {
  const { user } = useContext(UserContext);
  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(true);
  const [formData, setFormData] = useState({
    address: "",
    cardNumber: "",
    cardExpiry: "",
    cardCVV: "",
  });
  const [submitSuccess, setSubmitSuccess] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

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

  const handleCheckout = async () => {
    try {
      const checkoutData = {
        userId: user.id,
        cart: cart,
        shippingAddress: formData.address,
        billingAddress: formData.address,
        cardNumber: formData.cardNumber,
        cardExpiry: formData.cardExpiry,
        cardCVV: formData.cardCVV,
      };

      const response = await fetch('http://localhost:8080/user-checkout', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(checkoutData),
      });

      if (response.ok) {
        const data = await response.json();
        console.log('Checkout successful:', data);
        setSubmitSuccess(true); // Set the success state
      } else {
        console.error('Failed to complete checkout');
      }
    } catch (error) {
      console.error('Error during checkout:', error);
    }
  };

  if (loading) return <p>Loading...</p>;
  if (!cart) return <p>Cart not found</p>;

  return (
    <div className="max-w-md mx-auto bg-white shadow-lg rounded-lg p-6">
      <h2 className="text-2xl font-semibold text-gray-800 mb-4">User Checkout</h2>
      <div>
        <h3 className="text-lg font-semibold mb-2">Cart Items</h3>
        <ul>
          {cart.items.map((item) => (
            <li key={item.id} className="flex justify-between border-b-2 border-gray-200 py-2">
              <span className="text-base">{item.book.title}</span>
              <span className="text-base">Price: ${item.book.price}</span>
            </li>
          ))}
        </ul>
      </div>

      <div className="mt-4">
        <h3 className="text-lg font-semibold mb-2">Address</h3>
        <textarea
          value={formData.address}
          onChange={(e) => setFormData({ ...formData, address: e.target.value })}
          className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="cardNumber" className="block text-gray-700 font-semibold mb-2">
          Card Number
        </label>
        <input
          type="text"
          id="cardNumber"
          name="cardNumber"
          value={formData.cardNumber}
          onChange={handleChange}
          required
          className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="cardExpiry" className="block text-gray-700 font-semibold mb-2">
          Expiration Date
        </label>
        <input
          type="text"
          id="cardExpiry"
          name="cardExpiry"
          value={formData.cardExpiry}
          onChange={handleChange}
          required
          className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="cardCVV" className="block text-gray-700 font-semibold mb-2">
          CVV
        </label>
        <input
          type="text"
          id="cardCVV"
          name="cardCVV"
          value={formData.cardCVV}
          onChange={handleChange}
          required
          className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>
      <p className="text-lg font-semibold mt-4">Total Price: ${cart.totalPrice}</p>

      {submitSuccess ? (
        <p className="text-green-600 font-semibold">Checkout successful!</p>
      ) : (
        <button
          onClick={handleCheckout}
          className="w-full py-2 px-4 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 mt-4"
        >
          Complete Checkout
        </button>
      )}
    </div>
  );
};

export default UserCheckout;