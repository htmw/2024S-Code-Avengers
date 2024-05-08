import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function BookGrid() {
  const handleImageError = (event) => {
    event.target.src = "https://via.placeholder.com/300x200?text=No+Image";
  };

  const [books, setBooks] = useState([]);

  useEffect(() => {
    const fetchBooks = async () => {
      const response = await fetch("http://localhost:8080/books/popular");
      const data = await response.json();
      setBooks(data);
    };

    fetchBooks();
  }, []);

  const handleBookClick = (book) => {
    // Handle book click event
  };

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
      {books.map((book) => (
        <Link
          key={book.id}
          to={`/bookdetail/${book.id}`}
          className="bg-white shadow-md rounded-lg overflow-hidden cursor-pointer transition-transform duration-300 ease-in-out hover:transform hover:scale-105"
        >
          <div className="relative">
            <div className="rounded-lg overflow-hidden">
              <img
                src="https://demo.publishr.cloud/assets/common/images/edition_placeholder.png"
                alt={book.title}
                className="w-full h-64 object-cover"
                onError={handleImageError}
                style={{ objectFit: "contain" }}
              />
            </div>
          </div>
          <div className="p-4">
            <h3 className="text-lg font-semibold text-black mb-2">
              {book.title}
            </h3>
            <p className="text-gray-600 text-sm">{book.author}</p>
          </div>
        </Link>
      ))}
    </div>
  );
}

export default BookGrid;
