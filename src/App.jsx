import React from "react";
import {
  BrowserRouter as Router,
  Route,
  Link,
  Routes,
  useLocation,
} from "react-router-dom";
import LoginRegister from "./LoginRegister";
import SignIn from "./SignIn";
import SignUp from "./SignUp";
import Onboarding from "./Onboarding";

const Header = () => {
  const location = useLocation();
  const isSignInPage = location.pathname === "/signin";
  const isSignUpPage = location.pathname === "/signup";
  const isOnboardingPage = location.pathname === "/onboarding";

  return (
    <header className="bg-white shadow">
      <div className="mx-auto flex max-w-7xl items-center justify-between px-4 py-6 sm:px-6 lg:px-8">
        <Link to="/">
          <h1 className="text-3xl font-bold text-gray-900">Book Buddy</h1>
        </Link>
        {!isSignInPage && !isSignUpPage && !isOnboardingPage && (
          <div>
            <Link to="/signin">
              <button className="mr-4 rounded-md bg-blue-500 px-4 py-2 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
                Sign In
              </button>
            </Link>
            <Link to="/signup">
              <button className="rounded-md bg-green-500 px-4 py-2 text-white hover:bg-green-600 focus:outline-none focus:ring-2 focus:ring-green-500">
                Sign Up
              </button>
            </Link>
          </div>
        )}
      </div>
    </header>
  );
};

export default function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-100">
        <Header />
        <main>
          <div className="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
            <Routes>
              <Route path="/" element={<LoginRegister />} />
              <Route path="/signin" element={<SignIn />} />
              <Route path="/signup" element={<SignUp />} />
              <Route path="/onboarding" element={<Onboarding />} />
            </Routes>
          </div>
        </main>
        <footer className="bg-white">
          <div className="mx-auto max-w-7xl px-4 py-4 sm:px-6 lg:px-8">
            <p className="text-center text-gray-500">
              &copy; 2023 Book Buddy. All rights reserved.
            </p>
          </div>
        </footer>
      </div>
    </Router>
  );
}
