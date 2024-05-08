import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function BookGridsn({ userId }) {
  const handleImageError = (event) => {
    event.target.src = "https://via.placeholder.com/300x200?text=No+Image";
  };

  const [collections, setCollections] = useState([]);

  useEffect(() => {
    const fetchCollections = async () => {
      const response = await fetch(
        `http://localhost:8080/collections/get/${userId}`,
      );
      const data = await response.json();
      setCollections(data);
    };

    fetchCollections();
  }, [userId]);

  const handleCollectionClick = (collection) => {};

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
      {collections.map((collection) => (
        <Link
          key={collection.id}
          to={`/collection/${collection.id}`}
          className="bg-white rounded-lg shadow-md overflow-hidden cursor-pointer transition-transform duration-300 ease-in-out hover:transform hover:scale-105"
        >
          <div
            key={collection.id}
            className="bg-white rounded-lg shadow-md overflow-hidden cursor-pointer transition-transform duration-300 ease-in-out hover:transform hover:scale-105"
            onClick={() => handleCollectionClick(collection)}
          >
            <div className="relative">
              <img
                src="https://demo.publishr.cloud/assets/common/images/edition_placeholder.png"
                alt={collection.name}
                className="w-full h-64 object-cover"
                onError={handleImageError}
                style={{ objectFit: "contain" }}
              />
            </div>
            <div className="p-4">
              <h3 className="text-lg font-semibold text-gray-800 mb-2">
                {collection.name}
              </h3>
              <p className="text-gray-600 text-sm">
                {collection.books.length} books
              </p>
            </div>
          </div>
        </Link>
      ))}
    </div>
  );
}

export default BookGridsn;
