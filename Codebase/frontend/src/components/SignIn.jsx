import React, { useState, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { auth } from "/src/firebase";
import { signInWithEmailAndPassword } from "firebase/auth";
import UserContext from "./UserContext";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function SignIn() {
  const { fetchUserData } = useContext(UserContext);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const userCredential = await signInWithEmailAndPassword(
        auth,
        email,
        password,
      );
      console.log("User logged in successfully");

      await fetchUserData(email);
      toast.success("Sign in successful!");
      navigate("/UserProfile");
    } catch (error) {
      console.error("Error logging in user:", error);
      if (
        error.code === "auth/user-not-found" ||
        error.code === "auth/wrong-password"
      ) {
        toast.error("Invalid email or password. Please try again.");
      } else {
        toast.error("An error occurred during sign in. Please try again.");
      }
    }
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <div className="w-full max-w-md">
        <div className="bg-white shadow-md rounded px-12 pt-10 pb-12 mb-4">
          <h2 className="text-3xl font-bold mb-8 text-center">Sign In</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-6">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="email"
              >
                Email
              </label>
              <input
                className="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>

            <div className="mb-8">
              <label
                className="block text-gray-700 text-sm font-bold mb-2"
                htmlFor="password"
              >
                Password
              </label>
              <input
                className="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>

            <div className="flex items-center justify-between">
              <button
                className="bg-orange-500 hover:bg-orange-600 text-white font-bold py-3 px-6 rounded-lg focus:outline-none focus:shadow-outline"
                type="submit"
              >
                Sign In
              </button>
              <Link
                to="/forgot-password"
                className="inline-block align-baseline font-bold text-sm text-orange-500 hover:text-orange-600"
              >
                Forgot Password?
              </Link>
            </div>
          </form>
          <p className="text-center text-gray-600 text-base mt-6">
            Don't have an account?{" "}
            <Link
              to="/signup"
              className="text-orange-500 hover:text-orange-600 font-bold"
            >
              Sign Up
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

export default SignIn;
