import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const SearchScreen = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [books, setBooks] = useState([]);
  const [selectedBooks, setSelectedBooks] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const response = await axios.get(`http://localhost:5001/search?q=${searchTerm}`);
        setBooks(response.data);
      } catch (error) {
        console.error('Error fetching books:', error);
      }
    };

    if (searchTerm) {
      fetchBooks();
    } else {
      setBooks([]);
    }
  }, [searchTerm]);

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleBookSelection = (book) => {
    if (selectedBooks.includes(book.Book)) {
      setSelectedBooks(selectedBooks.filter((b) => b !== book.Book));
    } else {
      setSelectedBooks([...selectedBooks, book.Book]);
    }
  };

  const handleSubmit = () => {
    navigate('/home', { state: { selectedBooks } });
  };

  return (
    <div className="flex flex-col items-center mt-10">
      <div className="max-w-lg w-full relative mb-4">
        <input
          type="text"
          placeholder="Search for books..."
          value={searchTerm}
          onChange={handleSearchChange}
          className="w-full px-4 py-2 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 text-lg"
        />
        <button className="absolute right-2 top-1 bg-blue-500 text-white rounded-full px-4 py-2 focus:outline-none hover:bg-blue-600">
          <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
        </button>
      </div>

      {selectedBooks.length >= 3 && (
        <button
          className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600 mb-4"
          onClick={handleSubmit}
        >
          Submit
        </button>
      )}

      {books.length > 0 && (
        <div className="mt-6 w-full max-w-4xl">
          {books.map((book, index) => (
            <div key={index} className="bg-white rounded-lg shadow-md p-4 mb-4 flex justify-between items-center">
              <div>
                <h3 className="text-xl font-bold">{book.Book}</h3>
                <p className="text-gray-600">Author: {book.Author}</p>
              </div>
              <button
                className={`bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 ${
                  selectedBooks.includes(book.Book) ? 'bg-green-500 hover:bg-green-600' : ''
                }`}
                onClick={() => handleBookSelection(book)}
              >
                {selectedBooks.includes(book.Book) ? 'Selected' : 'Add'}
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default SearchScreen;