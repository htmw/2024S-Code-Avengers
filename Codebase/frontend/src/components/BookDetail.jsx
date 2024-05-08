import React, { useState, useEffect, useContext } from "react";
import { useParams, Link } from "react-router-dom";
import { UserContext } from "./UserProvider";

function BookDetail() {
  const { id } = useParams();
  const { user, addToCart } = useContext(UserContext);
  const [book, setBook] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const [collectionName, setCollectionName] = useState("");
  const [recommendedBooks, setRecommendedBooks] = useState([]);

  useEffect(() => {
    const fetchBookDetail = async () => {
      try {
        const response = await fetch(`http://localhost:8080/books/${id}`);
        const data = await response.json();
        setBook(data);

        const recommendationsResponse = await fetch(
          `http://127.0.0.1:5001/recommendations/${encodeURIComponent(data.title)}?n=4`,
        );
        const recommendationsData = await recommendationsResponse.json();
        setRecommendedBooks(recommendationsData);
      } catch (error) {
        console.error("Error fetching book details:", error);
      }
    };
    fetchBookDetail();
  }, [id]);

  const handleAddToCart = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/cart/add-item/${user.cartId}/${id}`,
        { method: "POST" },
      );
      if (!response.ok) {
        throw new Error("Failed to add item to cart");
      }
    } catch (error) {
      console.error("Error adding item to cart:", error);
    }
  };

  const handleAddToCollection = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/collections/${user.id}/${collectionName}/add_book/${id}`,
        { method: "PUT" },
      );
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      console.log("Added to collection:", book.title);
    } catch (error) {
      console.error("Error adding to collection:", error);
    }
  };

  if (!book) {
    return <div>Loading...</div>;
  }

  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden">
      <div className="relative">
        <img
          src={
            book.image ||
            "https://demo.publishr.cloud/assets/common/images/edition_placeholder.png"
          }
          alt={book.title}
          className="w-full h-64 object-cover"
          style={{ objectFit: "contain" }}
        />
      </div>
      <div className="p-4">
        <h3 className="text-lg font-semibold text-gray-800 mb-2">
          {book.title}
        </h3>
        <p className="text-gray-600">{book.author}</p>
        <br />
        <p className="text-gray-800">{book.description}</p>
        <br />
        <p className="text-gray-800">Price: ${book.price}</p>
        {user ? (
          <>
            <div className="mt-4">
              <label
                htmlFor="collectionName"
                className="block font-medium text-gray-700"
              >
                Collection Name:
              </label>
              <input
                type="text"
                id="collectionName"
                value={collectionName}
                onChange={(e) => setCollectionName(e.target.value)}
                className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
              />
            </div>
            <button
              className="bg-blue-500 text-white px-4 py-2 rounded-lg mt-4"
              onClick={handleAddToCollection}
            >
              Add to Collection
            </button>
            <button
              className="bg-blue-500 text-white px-4 py-2 rounded-lg mt-4 ml-2"
              onClick={handleAddToCart}
            >
              Add to Cart
            </button>
          </>
        ) : (
          <Link to="/guestcheckout">
            <button className="bg-blue-500 text-white px-4 py-2 rounded-lg mt-4">
              Proceed to Guest Checkout
            </button>
          </Link>
        )}
      </div>

      {recommendedBooks.length > 0 && (
        <div className="mt-8">
          <h2 className="text-2xl font-semibold mb-4 ml-4">
            Recommended Books
          </h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
            {recommendedBooks.map((book) => (
              <Link
                key={book.Book}
                to={`/bookdetail/${book.Book}`}
                className="bg-white shadow-md rounded-lg overflow-hidden cursor-pointer transition-transform duration-300 ease-in-out hover:transform hover:scale-105"
              >
                <div className="relative">
                  <div className="rounded-lg overflow-hidden">
                    <img
                      src="https://demo.publishr.cloud/assets/common/images/edition_placeholder.png"
                      alt={book.Book}
                      className="w-full h-64 object-cover"
                      style={{ objectFit: "contain" }}
                    />
                  </div>
                </div>
                <div className="p-4">
                  <h3 className="text-lg font-semibold text-black mb-2">
                    {book.Book}
                  </h3>
                  <p className="text-gray-600 text-sm">{book.Author}</p>
                </div>
              </Link>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

export default BookDetail;
