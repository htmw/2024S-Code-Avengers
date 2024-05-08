import React, { useState, useEffect, useContext } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { UserContext } from "./UserContext";

function UserProfile() {
  const { userId } = useParams();
  const navigate = useNavigate();
  const { user, fetchUserData } = useContext(UserContext);
  const [collections, setCollections] = useState([]);

  useEffect(() => {
    fetchUserData(userId);
    fetchUserCollections();
  }, [userId, fetchUserData]);

  const fetchUserCollections = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/collections/get/${userId}`,
      );
      const data = await response.json();
      setCollections(data);
    } catch (error) {
      console.error("Error fetching user collections:", error);
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

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  if (!user) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold">User Profile</h1>
        <button
          className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded"
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
              <Link
                to={`/collections/${collection.id}`}
                className="text-blue-500 hover:underline"
              >
                {collection.name}
              </Link>
              <button
                className="ml-4 text-red-500 hover:text-red-700"
                onClick={() => handleDeleteCollection(collection.id)}
              >
                Delete
              </button>
            </li>
          ))}
        </ul>
      ) : (
        <p>No collections found.</p>
      )}
      <Link
        to={`/collections/${userId}/new`}
        className="mt-4 inline-block bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded"
      >
        Create New Collection
      </Link>
    </div>
  );
}

export default UserProfile;
