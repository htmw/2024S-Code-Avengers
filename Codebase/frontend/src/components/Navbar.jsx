import React from "react";
import { Link } from "react-router-dom";
import { auth } from "../firebase";
import { signOut } from "firebase/auth";
import { useNavigate } from "react-router-dom";

function Navbar({ isAuthenticated }) {
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await signOut(auth);
      navigate("/signin");
      window.location.reload();
    } catch (error) {
      console.error("Error logging out:", error);
    }
  };

  return (
    <nav className="bg-black shadow">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center py-4">
          <div>
            <Link to="/">
              <img
                src="https://raw.githubusercontent.com/htmw/2024S-Code-Avengers/main/Book%20Buddy%20Logo.png"
                alt="Logo"
                className="h-10"
              />
            </Link>
          </div>
          <div className="flex items-center">
            {isAuthenticated ? (
              <>
                <Link
                  to="/UserProfile"
                  className="text-white hover:text-gray-200 font-semibold py-2 px-4 rounded-lg transition duration-300 ease-in-out"
                >
                  Profile
                </Link>
                <button
                  className="bg-orange-500 hover:bg-orange-600 text-white font-semibold py-2 px-4 rounded-lg shadow-md ml-4 transition duration-300 ease-in-out"
                  onClick={handleLogout}
                >
                  Logout
                </button>
              </>
            ) : (
              <>
                <Link
                  to="/signin"
                  className="text-white hover:text-gray-200 font-semibold py-2 px-4 rounded-lg transition duration-300 ease-in-out"
                >
                  Sign In
                </Link>
                <Link
                  to="/signup"
                  className="bg-orange-500 hover:bg-orange-600 text-white font-semibold py-2 px-4 rounded-lg shadow-md ml-4 transition duration-300 ease-in-out"
                >
                  Sign Up
                </Link>
              </>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
