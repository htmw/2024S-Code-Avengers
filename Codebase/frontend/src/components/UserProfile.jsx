// import React, { useState, useEffect, useContext } from "react";
// import { useParams, Link, useNavigate } from "react-router-dom";
// import { UserContext } from "./UserContext";
// import { auth } from "../firebase";
// import { signOut } from "firebase/auth";

// function UserProfile() {
//   const navigate = useNavigate();
//   const { user, fetchUserData } = useContext(UserContext);
//   const [collections, setCollections] = useState([]);

//   useEffect(() => {
//     if (user) {
//       fetchUserCollections();
//     }
//   }, [user, fetchUserData]);

//   const fetchUserCollections = async () => {
//     try {
//       const response = await fetch(
//         `http://localhost:8080/collections/get/${user.id}`,
//       );
//       const data = await response.json();
//       setCollections(data);
//     } catch (error) {
//       console.error("Error fetching user collections:", error);
//       console.error("Error message:", error.message);
//       console.error("Error stack trace:", error.stack);
//     }
//   };

//   const handleDeleteCollection = async (collectionId) => {
//     try {
//       await fetch(`http://localhost:8080/collections/${collectionId}`, {
//         method: "DELETE",
//       });
//       setCollections(
//         collections.filter((collection) => collection.id !== collectionId),
//       );
//     } catch (error) {
//       console.error("Error deleting collection:", error);
//     }
//   };

//   const handleLogout = async () => {
//     try {
//       await signOut(auth);
//       navigate("/signin");
//     } catch (error) {
//       console.error("Error logging out:", error);
//     }
//   };

//   if (!user) {
//     return <div>Loading...</div>;
//   }

//   return (
//     <div className="container mx-auto px-4 py-8">
//       <div className="flex justify-between items-center mb-8">
//         <h1 className="text-3xl font-bold">User Profile</h1>
//         <button
//           className="bg-orange-500 hover:bg-orange-600 text-white font-semibold py-2 px-4 rounded"
//           onClick={handleLogout}
//         >
//           Logout
//         </button>
//       </div>
//       <div className="mb-8">
//         <p>
//           <strong>Name:</strong> {user.firstName} {user.lastName}
//         </p>
//         <p>
//           <strong>Email:</strong> {user.email}
//         </p>
//         <p>
//           <strong>Date of Birth:</strong> {user.dateOfBirth}
//         </p>
//       </div>
//       <h2 className="text-2xl font-bold mb-4">Book Collections</h2>
//       {collections.length > 0 ? (
//         <ul>
//           {collections.map((collection) => (
//             <li key={collection.id} className="mb-4">
//               <Link
//                 to={`/collections/${collection.id}`}
//                 className="text-blue-500 hover:underline"
//               >
//                 {collection.name}
//               </Link>
//               <button
//                 className="ml-4 text-red-500 hover:text-red-700"
//                 onClick={() => handleDeleteCollection(collection.id)}
//               >
//                 Delete
//               </button>
//             </li>
//           ))}
//         </ul>
//       ) : (
//         <p>No collections found.</p>
//       )}
//       <Link
//         to={`/collections/${user.id}/new${collectionName}`}
//         className="mt-4 inline-block bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded"
//       >
//         Create New Collection
//       </Link>
//     </div>
//   );
// }

// export default UserProfile;
import React, { useState, useEffect, useContext } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { UserContext } from "./UserContext";
import { auth } from "../firebase";
import { signOut } from "firebase/auth";

function UserProfile() {
  const navigate = useNavigate();
  const { user, fetchUserData } = useContext(UserContext);
  const [collections, setCollections] = useState([]);
  const [collectionName, setCollectionName] = useState("");

  useEffect(() => {
    if (user) {
      fetchUserCollections();
    }
  }, [user, fetchUserData]);

  const fetchUserCollections = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/collections/get/${user.id}`,
      );
      const data = await response.json();
      console.log("data:", data);
      setCollections(data);
    } catch (error) {
      console.error("Error fetching user collections:", error);
      console.error("Error message:", error.message);
      console.error("Error stack trace:", error.stack);
    }
  };

  const handleDeleteCollection = async (collectionId) => {
    try {
      await fetch(`http://localhost:8080/collections/${collectionId}`, {
        method: "DELETE",
      });
      setCollections(
        collections.filter((collection) => collection.id !== collectionId),
      );
    } catch (error) {
      console.error("Error deleting collection:", error);
    }
  };

  const handleLogout = async () => {
    try {
      await signOut(auth);
      navigate("/signin");
    } catch (error) {
      console.error("Error logging out:", error);
    }
  };

  const handleCreateCollection = async () => {
    try {
      const response = await fetch(`http://localhost:8080/collections/${user.id}/new/${collectionName}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userId: user.id,
          name: collectionName,
        }),
      });
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const newCollection = await response.json();
      setCollections([...collections, newCollection]);
      setCollectionName("");
    } catch (error) {
      console.error("Error creating collection:", error);
    }
  };

  if (!user) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold">User Profile</h1>
        <button
          className="bg-orange-500 hover:bg-orange-600 text-white font-semibold py-2 px-4 rounded"
          onClick={handleLogout}
        >
          Logout
        </button>
      </div>
      <div className="mb-8">
        <p>
          <strong>Name:</strong> {user.firstName} {user.lastName}
        </p>
        <p>
          <strong>Email:</strong> {user.email}
        </p>
        <p>
          <strong>Date of Birth:</strong> {user.dateOfBirth}
        </p>
      </div>
      <h2 className="text-2xl font-bold mb-4">Book Collections</h2>
      {collections.length > 0 ? (
        <ul>
          {collections.map((collection) => (
            <li key={collection.id} className="mb-4">
              <Link to={`/collections/${collection.id}`}>
                {collection.collectionName}
              </Link>
              <button
                onClick={() => handleDeleteCollection(collection.id)}
                className="ml-2 p-2 bg-red-500 hover:bg-red-600 text-white font-bold rounded"
              >
                Delete
              </button>
            </li>
          ))}
        </ul>
      ) : (
        <p>No collections found.</p>
      )}
      <div className="flex">
        <input
          type="text"
          className="mr-2 p-2 border rounded"
          placeholder="Collection Name"
          value={collectionName}
          onChange={(e) => setCollectionName(e.target.value)}
        />
        <button
          onClick={handleCreateCollection}
          className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded"
        >
          Create New Collection
        </button>
      </div>
    </div>
  );
}

export default UserProfile;
