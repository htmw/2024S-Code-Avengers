import React, { useState, useEffect } from 'react';

const UserContext = React.createContext(null);

const UserProvider = ({ children }) => {
    const [user, setUser] = useState({
        id: '',
        firstName: '',
        lastName: '',
        email: '',
        dateOfBirth: '',
        cartId: ''
    });

const fetchUserData = async (email) => {
  try {
    const response = await fetch(`https://localhost:8080/users/email/${email}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const userData = await response.json();
    setUser(userData);
  } catch (error) {
    console.error('Failed to fetch user data:', error);
  }
};

  return (
    <UserContext.Provider value={{ user, setUser, fetchUserData }}>
      {children}
    </UserContext.Provider>
  );
};

export { UserContext, UserProvider };
