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
      // Display an error message or perform any other necessary actions
    }
  };

  return (
    <div className="flex min-h-screen flex-col items-center justify-center bg-gray-100">
      <div className="w-full max-w-md rounded-lg bg-white px-8 py-10 shadow-md">
        <h2 className="mb-8 text-center text-3xl font-bold">
          Log in to your account
        </h2>
        <div className="mb-8 flex justify-center space-x-6">
          <button className="rounded-md bg-blue-500 p-3 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
            <FaFacebook className="h-6 w-6" />
          </button>
          <button className="rounded-md bg-red-500 p-3 text-white hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-500">
            <FaGoogle className="h-6 w-6" />
          </button>
        </div>
        <div className="mb-8 flex items-center">
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
              className="w-full rounded-md border border-gray-300 px-4 py-2 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div className="mb-6">
            <input
              type="password"
              placeholder="Your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full rounded-md border border-gray-300 px-4 py-2 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div className="mb-8 flex items-center">
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
            className="mb-6 w-full rounded-md bg-blue-500 px-4 py-2 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            Log In
          </button>
        </form>
        <div className="text-center text-sm text-gray-600">
          Don't have an account?{" "}
          <Link to="/signup">
            <button className="font-bold text-blue-500 hover:underline">
              Sign up
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default SignIn;