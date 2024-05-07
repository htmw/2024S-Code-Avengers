import React from "react";
import { books } from "./data.js";

function BookGrid() {
  const handleImageError = (event) => {
    event.target.src = "https://via.placeholder.com/300x200?text=No+Image";
  };

  const handleBookClick = (book) => {
    // Handle the click event for the book
    console.log("Clicked book:", book);
  };

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
      {books.map((book) => (
        <div
          key={book.id}
          className="bg-white rounded-lg shadow-md overflow-hidden cursor-pointer transition-transform duration-300 ease-in-out hover:transform hover:scale-105"
          onClick={() => handleBookClick(book)}
        >
          <div className="relative">
            <img
              src={book.image}
              alt={book.title}
              className="w-full h-64 object-cover"
              onError={handleImageError}
            />
          </div>
          <div className="p-4">
            <h3 className="text-lg font-semibold text-gray-800 mb-2">
              {book.title}
            </h3>
            <p className="text-gray-600 text-sm">{book.author}</p>
          </div>
        </div>
      ))}
    </div>
  );
}

export default BookGrid;
