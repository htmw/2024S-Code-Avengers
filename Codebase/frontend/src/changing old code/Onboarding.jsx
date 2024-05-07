import React from "react";
import { useNavigate } from "react-router-dom";

const Onboarding = () => {
  const navigate = useNavigate();

  const handleOptionClick = (option) => {
    if (option === "newReader") {
      navigate("/genre-selection");
    } else if (option === "experiencedReader") {
      navigate("/search-screen");
    }
  };

  return (
    <div className="min-h-screen bg-black flex flex-col items-center justify-center">
      <div className="w-full max-w-md rounded-lg bg-white/10 backdrop-blur-md px-8 py-10 shadow-lg">
        <h2 className="mb-8 text-center text-3xl font-bold text-red-600">
          Continue here to get your first recommendations
        </h2>
        <p className="mb-8 text-center text-lg text-gray-400">
          Choose the option that best describes you:
        </p>
        <div className="space-y-6">
          <button
            onClick={() => handleOptionClick("newReader")}
            className="w-full rounded-md bg-blue-500 px-4 py-3 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            I am a new reader and would like to fill out a survey about general
            likes and dislikes
          </button>
          <button
            onClick={() => handleOptionClick("experiencedReader")}
            className="w-full rounded-md bg-blue-500 px-4 py-3 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            I have read numerous books and would like to enter books I like and
            dislike
          </button>
        </div>
      </div>
    </div>
  );
};

export default Onboarding;
