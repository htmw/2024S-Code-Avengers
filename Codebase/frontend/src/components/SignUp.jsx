import React, { useState, useContext } from "react";
import { Link } from "react-router-dom";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { auth } from "/src/firebase";
import { createUserWithEmailAndPassword } from "firebase/auth";

function SignUp() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [description, setDescription] = useState("");
  const [genres, setGenres] = useState([]);
  const [birthMonth, setBirthMonth] = useState("");
  const [birthDate, setBirthDate] = useState("");
  const [birthYear, setBirthYear] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const genresOptions = [
    "Fiction",
    "Non-Fiction",
    "Mystery",
    "Romance",
    "Science Fiction",
    "Fantasy",
    "Thriller",
    "Biography",
    "History",
    "Self-Help",
  ];

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const userCredential = await createUserWithEmailAndPassword(
        auth,
        email,
        password,
      );
      const user = userCredential.user;
      console.log("User registered successfully:", user);

      if (user) {
        const nameParts = name.split(" ");
        const firstName = nameParts[0];
        const lastName =
          nameParts.length > 1 ? nameParts.slice(1).join(" ") : "";

        const paddedMonth = String(birthMonth).padStart(2, "0");
        const paddedDate = String(birthDate).padStart(2, "0");

        const dateOfBirth = `${birthYear}-${paddedMonth}-${paddedDate}`;
        const response = await fetch("http://localhost:8080/users/new", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            firstName,
            lastName,
            email,
            dateOfBirth,
          }),
        });
        const userData = await response.json();
        console.log("User data saved on backend:", userData);
      }
    } catch (error) {
      console.error("Error registering user:", error);
    }
  };

  const handleGenreChange = (genre) => {
    if (genres.includes(genre)) {
      setGenres(genres.filter((g) => g !== genre));
    } else {
      setGenres([...genres, genre]);
    }
  };

  const renderGenreOptions = () => {
    return genresOptions.map((genre) => (
      <div key={genre} className="flex items-center">
        <input
          type="checkbox"
          id={genre}
          checked={genres.includes(genre)}
          onChange={() => handleGenreChange(genre)}
          className="mr-2"
        />
        <label htmlFor={genre}>{genre}</label>
      </div>
    ));
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <div className="w-full max-w-md">
        <div className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
          <h2 className="text-2xl font-bold mb-6 text-center">Sign Up</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="name"
              >
                Name
              </label>
              <input
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="name"
                type="text"
                placeholder="Full Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>

            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="Email"
              >
                Email Address
              </label>
              <input
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="Email"
                type="email"
                placeholder="Email Address"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>

            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="description"
              >
                Description
              </label>
              <textarea
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="description"
                rows="3"
                placeholder="Description what type of book you want to read and like even favorite book."
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
            </div>

            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="genres"
              >
                Favorite Genres
              </label>
              <div className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                {renderGenreOptions()}
              </div>
            </div>

            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="dateOfBirth"
              >
                What's your date of birth?
              </label>
              <DatePicker
                selected={
                  birthYear && birthMonth && birthDate
                    ? new Date(birthYear, birthMonth - 1, birthDate)
                    : null
                }
                onChange={(date) => {
                  setBirthYear(date.getFullYear());
                  setBirthMonth(date.getMonth() + 1);
                  setBirthDate(date.getDate());
                }}
                dateFormat="yyyy-MM-dd"
                className="w-full rounded-md border border-gray-300 px-4 py-2 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            <div className="mb-4">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="password"
              >
                Password
              </label>
              <input
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="password"
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>

            <div className="mb-6">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="confirmPassword"
              >
                Confirm Password
              </label>
              <input
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="confirmPassword"
                type="password"
                placeholder="Confirm Password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
              />
            </div>

            <div className="flex items-center justify-between">
              <button
                className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                type="submit"
              >
                Sign Up
              </button>
            </div>
          </form>
          <p className="text-center text-gray-500 text-sm mt-4">
            Already have an account?{" "}
            <Link
              to="/signin"
              className="text-blue-500 hover:text-blue-600 font-bold"
            >
              Sign In
            </Link>
          </p>
        </div>
        <p className="text-center text-gray-500 text-xs">
          &copy; 2024 Book Buddy. All rights reserved.
        </p>
      </div>
    </div>
  );
}

export default SignUp;
