import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

function CollectionDetail() {
  const { id } = useParams();
  const [collection, setCollection] = useState(null);

  useEffect(() => {
    const fetchCollection = async () => {
      try {
        const response = await fetch(`http://localhost:8080/collections/${id}`);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        setCollection(data);
      } catch (error) {
        console.error('Error fetching collection:', error);
      }
    };

    fetchCollection();
  }, [id]);

  if (!collection) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h2>Collection Details</h2>
      <p>ID: {collection.id}</p>
      <p>Name: {collection.collectionName}</p>
    </div>
  );
}

export default CollectionDetail;
