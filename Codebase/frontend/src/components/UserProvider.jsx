import React, { useState } from "react";
import UserContext from "./UserContext";

const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  const fetchUserData = async (email) => {
    try {
      const response = await fetch(
        `https://localhost:8080/users/email/${email}`,
      );
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const userData = await response.json();
      setUser(userData);
    } catch (error) {
      console.error("Failed to fetch user data:", error);
    }
  };

  const addToCart = async (book, quantity) => {
    if (user) {
      try {
        const response = await fetch("https://localhost:8080/cart", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            userId: user.id,
            bookId: book.id,
            quantity: quantity,
          }),
        });
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        console.log("Book added to cart:", data);
      } catch (error) {
        console.error("Failed to add book to cart:", error);
      }
    }
  };

  return (
    <UserContext.Provider value={{ user, fetchUserData, addToCart }}>
      {children}
    </UserContext.Provider>
  );
};

export { UserContext };
export default UserProvider;
