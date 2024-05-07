import React, { useState, useEffect, useContext } from "react";
import { useParams, Link } from "react-router-dom";
import { UserContext } from "./UserProvider"; // Assuming you have a UserProvider that provides the user's authentication status

function BookDetail() {
  const { id } = useParams();
  const { user } = useContext(UserContext); // Assume user is an object that contains the user's information, including authentication status

  const [book, setBook] = useState(null);
  const [quantity, setQuantity] = useState(1);

  useEffect(() => {
    const fetchBookDetail = async () => {
      const response = await fetch(`http://localhost:8080/books/${id}`);
      const data = await response.json();
      setBook(data);
    };

    fetchBookDetail();
  }, [id]);

  const handleAddToCart = async () => {
    if (user && user.isAuthenticated) {
      // User is signed in, add to cart logic
      console.log("Adding to cart:", book.title);
    }
    // No need for else condition as the link will only be rendered if user is not signed in
  };

  if (!book) {
    return <div>Loading...</div>;
  }

  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden">
      <div className="relative">
        <img
          src="https://demo.publishr.cloud/assets/common/images/edition_placeholder.png"
          alt={book.title}
          className="w-full h-64 object-cover"
          style={{ objectFit: 'contain' }}
        />
      </div>
      <div className="p-4">
        <h3 className="text-lg font-semibold text-gray-800 mb-2">
          {book.title}
        </h3>
        <p className="text-gray-600 text-sm">{book.author}</p>
        <p className="text-gray-600">{book.description}</p>
        {user && user.isAuthenticated ? (
          <button
            className="bg-blue-500 text-white px-4 py-2 rounded-lg mt-4"
            onClick={handleAddToCart}
          >
            Add to Cart
          </button>
        ) : (
          <Link to="/guest-checkout">
            <button className="bg-blue-500 text-white px-4 py-2 rounded-lg mt-4">
              Proceed to Guest Checkout
            </button>
          </Link>
        )}
      </div>
    </div>
  );
}

export default BookDetail;
