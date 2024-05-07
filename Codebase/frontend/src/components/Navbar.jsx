import React from "react";
import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav className="bg-white shadow">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center py-6">
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
            <Link
              to="/signin"
              className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-lg shadow-md transition duration-300 ease-in-out"
            >
              Sign In
            </Link>
            <Link
              to="/signup"
              className="bg-green-500 hover:bg-green-600 text-white font-semibold py-2 px-4 rounded-lg shadow-md ml-4 transition duration-300 ease-in-out"
            >
              Sign Up
            </Link>
          </div>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
