import React from "react";

function Navbar() {
  return (
    <nav className="bg-white shadow">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center py-6">
          <div>
            <img
              src="https://raw.githubusercontent.com/htmw/2024S-Code-Avengers/main/Book%20Buddy%20Logo.png"
              alt="Logo"
              className="h-10"
            />
          </div>
          <div className="flex items-center">
            <button className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-lg shadow-md transition duration-300 ease-in-out">
              Sign In
            </button>
            <button className="bg-green-500 hover:bg-green-600 text-white font-semibold py-2 px-4 rounded-lg shadow-md ml-4 transition duration-300 ease-in-out">
              Sign Up
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
