import React, { useState, useEffect } from "react";
import {
  BrowserRouter as Router,
  Route,
  Link,
  Routes,
  Navigate,
} from "react-router-dom";
import { auth } from "./firebase";
import { onAuthStateChanged } from "firebase/auth";

import LoginRegister from "./LoginRegister";
import SignIn from "./SignIn";
import SignUp from "./SignUp";
import Onboarding from "./Onboarding";
import GenreSelection from "./GenreSelection";
import SearchScreen from "./SearchScreen";
import HomeScreen from "./HomeScreen";
import ProfileScreen from "./ProfileScreen";

const Header = ({ isLoggedIn, isSignInPage, isSignUpPage, isProfilePage }) => {
  return (
    <header className="bg-black">
      <div className="mx-auto flex max-w-7xl items-center justify-between px-4 py-6 sm:px-6 lg:px-8">
        <Link to="/">
          <h1 className="text-3xl font-bold text-red-600">Book Buddy</h1>
        </Link>
        <div>
          {isLoggedIn ? (
            <>
              {!isProfilePage && (
                <Link to="/profile">
                  <button className="mr-4 rounded-full bg-white/20 backdrop-blur-sm px-6 py-3 text-white font-semibold transition duration-300 ease-in-out hover:bg-white/30 focus:outline-none focus:ring-2 focus:ring-white/30">
                    Profile
                  </button>
                </Link>
              )}
            </>
          ) : (
            <>
              {!isSignInPage && (
                <Link to="/signin">
                  <button className="mr-4 rounded-full bg-white/20 backdrop-blur-sm px-6 py-3 text-white font-semibold transition duration-300 ease-in-out hover:bg-white/30 focus:outline-none focus:ring-2 focus:ring-white/30">
                    Sign In
                  </button>
                </Link>
              )}
              {!isSignUpPage && (
                <Link to="/signup">
                  <button className="rounded-full bg-white/20 backdrop-blur-sm px-6 py-3 text-white font-semibold transition duration-300 ease-in-out hover:bg-white/30 focus:outline-none focus:ring-2 focus:ring-white/30">
                    Sign Up
                  </button>
                </Link>
              )}
            </>
          )}
        </div>
      </div>
    </header>
  );
};

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, (user) => {
      setIsLoggedIn(!!user);
    });

    return () => {
      unsubscribe();
    };
  }, []);

  return (
    <Router>
      <div className="min-h-screen bg-black">
        <Header
          isLoggedIn={isLoggedIn}
          isSignInPage={window.location.pathname === "/signin"}
          isSignUpPage={window.location.pathname === "/signup"}
          isProfilePage={window.location.pathname === "/profile"}
        />
        <main>
          <div className="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
            <Routes>
              <Route path="/" element={<LoginRegister />} />
              <Route path="/signin" element={<SignIn />} />
              <Route path="/signup" element={<SignUp />} />
              <Route
                path="/onboarding"
                element={
                  isLoggedIn ? <Onboarding /> : <Navigate to="/signin" />
                }
              />
              <Route
                path="/genre-selection"
                element={
                  isLoggedIn ? <GenreSelection /> : <Navigate to="/signin" />
                }
              />
              <Route
                path="/search-screen"
                element={
                  isLoggedIn ? <SearchScreen /> : <Navigate to="/signin" />
                }
              />
              <Route
                path="/home"
                element={
                  isLoggedIn ? <HomeScreen /> : <Navigate to="/signin" />
                }
              />
              <Route
                path="/profile"
                element={
                  isLoggedIn ? <ProfileScreen /> : <Navigate to="/signin" />
                }
              />
            </Routes>
          </div>
        </main>
        <footer className="bg-black">
          <div className="mx-auto max-w-7xl px-4 py-4 sm:px-6 lg:px-8">
            <p className="text-center text-white/80">
              &copy; 2023 Book Buddy. All rights reserved.
            </p>
          </div>
        </footer>
      </div>
    </Router>
  );
}
