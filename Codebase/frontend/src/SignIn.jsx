import React, { useState } from "react";
import { FaFacebook, FaGoogle } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";
import { auth } from "./firebase";
import { signInWithEmailAndPassword } from "firebase/auth";

const SignIn = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [keepSignedIn, setKeepSignedIn] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await signInWithEmailAndPassword(auth, email, password);
      console.log("User logged in successfully");
      navigate("/onboarding");
    } catch (error) {
      console.error("Error logging in user:", error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center">
      <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
        <h2 className="text-3xl font-bold text-center mb-8">
          Log in to your account
        </h2>
        <div className="flex justify-center space-x-6 mb-8">
          <button className="bg-blue-500 text-white p-3 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
            <FaFacebook className="w-6 h-6" />
          </button>
          <button className="bg-red-500 text-white p-3 rounded-md hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-500">
            <FaGoogle className="w-6 h-6" />
          </button>
        </div>
        <div className="flex items-center mb-8">
          <hr className="w-full border-gray-300" />
          <span className="mx-4 text-gray-400">OR</span>
          <hr className="w-full border-gray-300" />
        </div>
        <form onSubmit={handleSubmit}>
          <div className="mb-6">
            <input
              type="email"
              placeholder="Email address"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            />
          </div>
          <div className="mb-6">
            <input
              type="password"
              placeholder="Your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            />
          </div>
          <div className="flex items-center mb-8">
            <input
              type="checkbox"
              checked={keepSignedIn}
              onChange={(e) => setKeepSignedIn(e.target.checked)}
              className="mr-2 h-4 w-4 text-blue-500 focus:ring-blue-500"
            />
            <label className="text-gray-600">
              Keep me signed in until I sign out
            </label>
          </div>
          <button
            type="submit"
            className="w-full bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 mb-6"
          >
            Log In
          </button>
        </form>
        <div className="text-center text-sm text-gray-600">
          Don't have an account?{" "}
          <Link to="/signup">
            <button className="text-blue-500 font-bold hover:underline">
              Sign up
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default SignIn;
