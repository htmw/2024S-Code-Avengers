import React from "react";

const BookBuddy = () => {
  return (
    <div className="min-h-screen bg-gradient-to-r from-indigo-500 via-purple-500 to-pink-500 flex items-center justify-center">
      <div className="w-full max-w-md rounded-lg bg-white/10 backdrop-blur-md shadow-lg p-8 text-center">
        <h1 className="text-5xl font-bold text-white mb-4">Book Buddy</h1>
        <p className="text-xl text-white/80 mb-8">
          Your ultimate reading companion
        </p>
        <button className="rounded-full bg-white/20 backdrop-blur-sm px-8 py-3 text-white font-semibold transition duration-300 ease-in-out hover:bg-white/30 focus:outline-none focus:ring-2 focus:ring-white/30">
          Get Started
        </button>
      </div>
    </div>
  );
};

export default BookBuddy;
