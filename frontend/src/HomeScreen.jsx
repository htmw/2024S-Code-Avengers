import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { db } from './firebase';
import { collection, addDoc } from 'firebase/firestore';
import axios from 'axios';

const HomeScreen = () => {
  const location = useLocation();
  const { selectedBooks } = location.state;
  const [recommendedBooks, setRecommendedBooks] = useState([]);

  useEffect(() => {
    const fetchRecommendedBooks = async () => {
      try {
        const recommendedBooksData = [];
        const addedBooks = new Set();

        for (const book of selectedBooks) {
          const response = await axios.get(`http://localhost:5001/recommendations/${encodeURIComponent(book)}?n=5`);

          response.data.forEach((book) => {
            if (!addedBooks.has(book.Book)) {
              recommendedBooksData.push(book);
              addedBooks.add(book.Book);
            }
          });
        }

        setRecommendedBooks(recommendedBooksData);
      } catch (error) {
        console.error('Error fetching recommended books:', error);
      }
    };

    fetchRecommendedBooks();
  }, [selectedBooks]);

  const handleLikeBook = async (book) => {
    try {
      await addDoc(collection(db, 'likedBooks'), book);
      console.log('Book liked successfully');
    } catch (error) {
      console.error('Error liking book:', error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100">
      <main>
        <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
          <div className="px-4 py-6 sm:px-0">
            <h2 className="text-2xl font-bold mb-4">Recommended Books</h2>
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
              {recommendedBooks.map((book, index) => (
                <div key={index} className="bg-white overflow-hidden shadow rounded-lg">
                  <div className="px-4 py-5 sm:p-6">
                    <img src={book.CoverImage} alt={book.Book} className="w-full h-64 object-cover rounded-lg mb-4" />
                    <h3 className="text-lg font-medium text-gray-900 mb-2">{book.Book}</h3>
                    <p className="text-gray-500 mb-2">Author: {book.Author}</p>
                    <p className="text-gray-700 mb-4 line-clamp-3">{book.Description}</p>
                    <div className="flex justify-between items-center">
                      <p className="text-gray-500">Genres: {book.Genres}</p>
                      <button
                        onClick={() => handleLikeBook(book)}
                        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                      >
                        Like
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default HomeScreen;