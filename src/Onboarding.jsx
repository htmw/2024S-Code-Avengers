import React from "react";

const Onboarding = () => {
  const handleOptionClick = (option) => {
    // Handle the selected option here
    console.log("Selected option:", option);
    // Redirect to the appropriate page or perform any other necessary actions
  };

  return (
    <div className="flex min-h-screen flex-col items-center justify-center bg-gray-100">
      <div className="w-full max-w-md rounded-lg bg-white px-8 py-10 shadow-md">
        <h2 className="mb-8 text-center text-3xl font-bold">
          Continue here to get your first recommendations
        </h2>
        <p className="mb-8 text-center text-lg text-gray-600">
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
