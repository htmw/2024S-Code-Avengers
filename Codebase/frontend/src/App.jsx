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
import GenreSelection from "./GenreSelection";
import SearchScreen from "./SearchScreen";
import HomeScreen from "./HomeScreen";

const Header = () => {
  const location = useLocation();
  const isSignInPage = location.pathname === "/signin";
  const isSignUpPage = location.pathname === "/signup";
  const isOnboardingPage = location.pathname === "/onboarding";
  const isGenreSelectionPage = location.pathname === "/genre-selection";
  const isSearchScreenPage = location.pathname === "/search-screen";

  return (
    <header className="bg-black">
      <div className="mx-auto flex max-w-7xl items-center justify-between px-4 py-6 sm:px-6 lg:px-8">
        <Link to="/">
          <h1 className="text-3xl font-bold text-red-600">Book Buddy</h1>
        </Link>
        {!isSignInPage &&
          !isSignUpPage &&
          !isOnboardingPage &&
          !isGenreSelectionPage &&
          !isSearchScreenPage && (
            <div>
              <Link to="/signin">
                <button className="mr-4 rounded-full bg-white/20 backdrop-blur-sm px-6 py-3 text-white font-semibold transition duration-300 ease-in-out hover:bg-white/30 focus:outline-none focus:ring-2 focus:ring-white/30">
                  Sign In
                </button>
              </Link>
              <Link to="/signup">
                <button className="rounded-full bg-white/20 backdrop-blur-sm px-6 py-3 text-white font-semibold transition duration-300 ease-in-out hover:bg-white/30 focus:outline-none focus:ring-2 focus:ring-white/30">
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
      <div className="min-h-screen bg-black">
        <Header />
        <main>
          <div className="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
            <Routes>
              <Route path="/" element={<LoginRegister />} />
              <Route path="/signin" element={<SignIn />} />
              <Route path="/signup" element={<SignUp />} />
              <Route path="/onboarding" element={<Onboarding />} />
              <Route path="/genre-selection" element={<GenreSelection />} />
              <Route path="/search-screen" element={<SearchScreen />} />
              <Route path="/home" element={<HomeScreen />} />
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
