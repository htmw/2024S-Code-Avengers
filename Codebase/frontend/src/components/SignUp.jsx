import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { auth, db } from "/src/firebase";
import { createUserWithEmailAndPassword } from "firebase/auth";
import { collection, addDoc } from "firebase/firestore";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

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

  const navigate = useNavigate();

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

    if (password !== confirmPassword) {
      toast.error("Passwords do not match.");
      return;
    }

    try {
      const userCredential = await createUserWithEmailAndPassword(
        auth,
        email,
        password,
      );
      const user = userCredential.user;
      console.log("User registered successfully:", user);

      const nameParts = name.split(" ");
      const firstName = nameParts[0];
      const lastName = nameParts.length > 1 ? nameParts.slice(1).join(" ") : "";

      const paddedMonth = String(birthMonth).padStart(2, "0");
      const paddedDate = String(birthDate).padStart(2, "0");

      const dateOfBirth = `${birthYear}-${paddedMonth}-${paddedDate}`;

      const userData = {
        userId: user.uid,
        firstName,
        lastName,
        email,
        dateOfBirth,
        description,
        genres,
      };

      await addDoc(collection(db, "users"), userData);
      console.log("User data saved in Firestore:", userData);

      const response = await fetch(
        "https://api.openai.com/v1/chat/completions",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer ",
          },
          body: JSON.stringify({
            model: "gpt-4",
            messages: [
              {
                role: "system",
                content:
                  "You are a helpful assistant that recommends books based on user preferences.",
              },
              {
                role: "user",
                content: `Based on the following user description and genres, please recommend 20 books in the format [[bookname1, author1, description1], [bookname2, author2, description2], ...]:

              Description: ${description}
              Genres: ${genres.join(", ")}`,
              },
            ],
          }),
        },
      );

      const data = await response.json();
      const recommendationsString = data.choices[0].message.content;
      console.log("Book recommendations:", recommendationsString);

      const recommendations = JSON.parse(recommendationsString);

      const recommendationsData = {
        userId: user.uid,
        recommendations,
      };
      await addDoc(collection(db, "recommendedBooks"), recommendationsData);
      console.log("Recommended books saved in Firestore:", recommendationsData);

      toast.success("Sign up successful!");
      navigate("/user");
    } catch (error) {
      console.error("Error registering user:", error);
      if (error.code === "auth/email-already-in-use") {
        toast.error("An account with this email already exists.");
      } else {
        toast.error("An error occurred during sign up. Please try again.");
      }
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
      <button
        key={genre}
        className={`px-4 py-2 rounded-md mr-2 mb-2 ${
          genres.includes(genre)
            ? "bg-orange-500 text-white"
            : "bg-gray-200 text-gray-700"
        }`}
        onClick={() => handleGenreChange(genre)}
      >
        {genre}
      </button>
    ));
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <div className="w-full max-w-3xl">
        <div className="bg-white shadow-md rounded px-12 pt-10 pb-12 mb-4">
          <h2 className="text-3xl font-bold mb-8 text-center">Sign Up</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-6">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="name"
              >
                Name
              </label>
              <input
                className="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="name"
                type="text"
                placeholder="Full Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>

            <div className="mb-6">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="Email"
              >
                Email Address
              </label>
              <input
                className="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="Email"
                type="email"
                placeholder="Email Address"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>

            <div className="mb-6">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="description"
              >
                Description
              </label>
              <textarea
                className="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="description"
                rows="4"
                placeholder="Describe what type of books you want to read and like, including your favorite books."
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
            </div>

            <div className="mb-6">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="genres"
              >
                Favorite Genres
              </label>
              <div className="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                <div className="flex flex-wrap">{renderGenreOptions()}</div>
              </div>
            </div>

            <div className="mb-6">
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
                className="w-full rounded-md border border-gray-300 px-4 py-3 focus:border-orange-500 focus:outline-none focus:ring-2 focus:ring-orange-500"
              />
            </div>

            <div className="mb-6">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="password"
              >
                Password
              </label>
              <input
                className="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="password"
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>

            <div className="mb-8">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="confirmPassword"
              >
                Confirm Password
              </label>
              <input
                className="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
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
                className="bg-orange-500 hover:bg-orange-600 text-white font-bold py-3 px-6 rounded-lg focus:outline-none focus:shadow-outline"
                type="submit"
              >
                Sign Up
              </button>
            </div>
          </form>
          <p className="text-center text-gray-600 text-base mt-6">
            Already have an account?{" "}
            <Link
              to="/signin"
              className="text-orange-500 hover:text-orange-600 font-bold"
            >
              Sign In
            </Link>
          </p>
        </div>
        <p className="text-center text-gray-500 text-sm">
          &copy; 2024 Book Buddy. All rights reserved.
        </p>
      </div>
      <ToastContainer />
    </div>
  );
}

export default SignUp;
