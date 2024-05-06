import React from "react";

const ProfileScreen = () => {
  const userName = "sandeep";
  const initialLetter = userName.charAt(0);

  return (
    <div className="bg-black text-white min-h-screen">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
        <div className="bg-gradient-to-r from-gray-800 to-gray-900 rounded-lg p-6">
          <div className="flex items-center mb-6">
            <div className="bg-red-600 text-white rounded-md flex items-center justify-center h-20 w-20 mr-6 text-3xl font-bold">
              {initialLetter}
            </div>
            <div>
              <h2 className="text-2xl font-bold">{userName}</h2>
              <p className="text-gray-400">sandeep@example.com</p>
            </div>
          </div>
          <div className="mb-6">
            <h3 className="text-xl font-bold mb-2">Bio</h3>
            <p className="text-gray-300">
              I'm a book lover and an avid reader. I enjoy reading a wide range
              of genres, from classics to contemporary fiction.
            </p>
          </div>
          <div className="mb-6">
            <h3 className="text-xl font-bold mb-2">Favorite Genres</h3>
            <div className="flex flex-wrap">
              <span className="bg-red-600 text-white rounded-sm px-3 py-1 mr-2 mb-2">
                Fiction
              </span>
              <span className="bg-green-600 text-white rounded-sm px-3 py-1 mr-2 mb-2">
                Mystery
              </span>
              <span className="bg-blue-600 text-white rounded-sm px-3 py-1 mr-2 mb-2">
                Fantasy
              </span>
            </div>
          </div>
          <div className="mb-6">
            <h3 className="text-xl font-bold mb-2">Favorite Books</h3>
            <ul className="list-disc list-inside text-gray-300">
              <li>To Kill a Mockingbird by Harper Lee</li>
              <li>The Lord of the Rings by J.R.R. Tolkien</li>
              <li>Pride and Prejudice by Jane Austen</li>
            </ul>
          </div>
          <button className="bg-red-600 text-white rounded-sm px-6 py-3 font-semibold transition duration-300 ease-in-out hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-400">
            Edit Profile
          </button>
        </div>
      </div>
    </div>
  );
};

export default ProfileScreen;
