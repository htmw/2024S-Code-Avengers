import React from "react";
import Navbar from "./components/Navbar";
import BookGrid from "./components/BookGrid";

function App() {
  return (
    <div className="min-h-screen bg-gray-100">
      <Navbar />
      <main className="container mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold mb-8">Welcome to Book Buddy</h1>
        <BookGrid />
      </main>
      <footer className="bg-white py-4">
        <div className="container mx-auto px-4 text-center">
          <p className="text-gray-600">
            &copy; 2023 Book Buddy. All rights reserved.
          </p>
        </div>
      </footer>
    </div>
  );
}

export default App;
