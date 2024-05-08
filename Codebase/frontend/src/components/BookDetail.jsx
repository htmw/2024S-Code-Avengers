// import React, { useState, useEffect, useContext } from "react";
// import { useParams, Link } from "react-router-dom";
// import { UserContext } from "./UserProvider";

// function BookDetail() {
//   const { id } = useParams();
//   const { user, addToCart } = useContext(UserContext);
//   const [book, setBook] = useState(null);
//   const [quantity, setQuantity] = useState(1);

//   useEffect(() => {
//     const fetchBookDetail = async () => {
//       const response = await fetch(`http://localhost:8080/books/${id}`);
//       const data = await response.json();
//       setBook(data);
//     };
//     fetchBookDetail();
//   }, [id]);

//   const handleAddToCart = () => {
//     if (user) {
//       addToCart(book, quantity);
//       console.log("Adding to cart:", book.title);
//     }
//   };

//   if (!book) {
//     return <div>Loading...</div>;
//   }

//   return (
//     <div className="bg-white rounded-lg shadow-md overflow-hidden">
//       <div className="relative">
//         <img
//           src={
//             book.image ||
//             "https://demo.publishr.cloud/assets/common/images/edition_placeholder.png"
//           }
//           alt={book.title}
//           className="w-full h-64 object-cover"
//           style={{ objectFit: "contain" }}
//         />
//       </div>
//       <div className="p-4">
//         <h3 className="text-lg font-semibold text-gray-800 mb-2">
//           {book.title}
//         </h3>
//         <p className="text-gray-600">{book.author}</p>
//         <br />
//         <p className="text-gray-800">{book.description}</p>
//         <br />
//         <p className="text-gray-800">Price: ${book.price}</p>
//         <div className="mt-4">
//           <label htmlFor="quantity" className="block font-medium text-gray-700">
//             Quantity:
//           </label>
//           <input
//             type="number"
//             id="quantity"
//             min="1"
//             value={quantity}
//             onChange={(e) => setQuantity(parseInt(e.target.value))}
//             className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
//           />
//         </div>
//         {user ? (
//           <button
//             className="bg-blue-500 text-white px-4 py-2 rounded-lg mt-4"
//             onClick={handleAddToCart}
//           >
//             Add to Cart
//           </button>
//         ) : (
//           <Link to="/guestcheckout">
//             <button className="bg-blue-500 text-white px-4 py-2 rounded-lg mt-4">
//               Proceed to Guest Checkout
//             </button>
//           </Link>
//         )}
//       </div>
//     </div>
//   );
// }

// export default BookDetail;

import React, { useState, useEffect, useContext } from "react";
import { useParams, Link } from "react-router-dom";
import { UserContext } from "./UserProvider";

function BookDetail() {
  const { id } = useParams();
  const { user, addToCart } = useContext(UserContext);
  const [book, setBook] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const [collectionName, setCollectionName] = useState("");

  useEffect(() => {
    const fetchBookDetail = async () => {
      const response = await fetch(`http://localhost:8080/books/${id}`);
      const data = await response.json();
      setBook(data);
    };
    fetchBookDetail();
  }, [id]);

  const handleAddToCart = () => {
    if (user) {
      addToCart(book, quantity);
      console.log("Adding to cart:", book.title);
    }
  };

  const handleAddToCollection = async () => {
    try {
      const response = await fetch(`http://localhost:8080/collections/${user.id}/${collectionName}/add_book/${id}`, {
        method: "PUT",
      });
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
        <div className="mt-4">
          <label htmlFor="quantity" className="block font-medium text-gray-700">
            Quantity:
          </label>
          <input
            type="number"
            id="quantity"
            min="1"
            value={quantity}
            onChange={(e) => setQuantity(parseInt(e.target.value))}
            className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
          />
        </div>
        {user ? (
          <>
            <div className="mt-4">
              <label htmlFor="collectionName" className="block font-medium text-gray-700">
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
    </div>
  );
}

export default BookDetail;
