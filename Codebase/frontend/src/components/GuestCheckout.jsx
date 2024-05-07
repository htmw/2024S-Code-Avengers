import React, { useState } from "react";
import { CardElement, useStripe, useElements, Elements } from "@stripe/react-stripe-js";

function GuestCheckout() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    address: "",
    paymentMethod: ""
  });

  const stripe = useStripe();
  const elements = useElements();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const cardElement = elements.getElement(CardElement);

    const { error, paymentMethod } = await stripe.createPaymentMethod({
      type: 'card',
      card: cardElement
    });

    if (!error) {
      // Process the payment on your server
      console.log("Processing payment:", paymentMethod);
    }
  };

  return (
    <div>
      <h2>Guest Checkout</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Name:
          <input type="text" name="name" value={formData.name} onChange={handleChange} required />
        </label>
        <br />
        <label>
          Email:
          <input type="email" name="email" value={formData.email} onChange={handleChange} />
        </label>
        <br />
        <label>
          Address:
          <textarea name="address" value={formData.address} onChange={handleChange} required />
        </label>
        <br />
        <label>
          Card Details:
          <CardElement />
        </label>
        <br />
        <button type="submit" disabled={!stripe}>Complete Purchase</button>
      </form>
    </div>
  );
}

export default GuestCheckout;
