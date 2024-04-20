import React, { useState } from "react";

const LoginRegister = () => {
  const [searchTerm, setSearchTerm] = useState("");

  const handleSearch = (e) => {
    e.preventDefault();
    // Perform search logic here
    console.log(`Searching for books related to: ${searchTerm}`);
    // Reset the search term after searching
    setSearchTerm("");
  };

  return (
    <div className="flex min-h-screen flex-col items-center justify-center bg-gray-100">
      <div className="w-full max-w-md rounded-lg bg-white px-8 py-10 shadow-md">
        <h1 className="mb-6 text-center text-3xl font-bold">
          Welcome to Book Buddy!
        </h1>
        <h2 className="mb-8 text-center text-xl">
          Find Your Next Favorite Book
        </h2>
        <form onSubmit={handleSearch} className="flex items-center">
          <input
            type="text"
            placeholder="Search for books..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="mr-4 w-full rounded-md border border-gray-300 bg-white px-4 py-2 text-gray-700 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <button
            type="submit"
            className="rounded-md bg-blue-500 px-6 py-2 text-white transition duration-300 ease-in-out hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
          >
            Search
          </button>
        </form>
      </div>
    </div>
  );
};

export default LoginRegister;
