import React, { createContext, useState } from "react";

export const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  const fetchUserData = async (userId) => {
    try {
      const response = await fetch(`http://localhost:8080/users/${userId}`);
      const data = await response.json();
      setUser(data);
    } catch (error) {
      console.error("Error fetching user data:", error);
    }
  };

  return (
    <UserContext.Provider value={{ user, fetchUserData }}>
      {children}
    </UserContext.Provider>
  );
};
