from data_loader import BookDataLoader
from text_encoder import TextEncoder
from faiss_indexer import FaissIndexer
import pandas as pd
from typing import List

class BookRecommender:
    def __init__(self, data_loader: BookDataLoader, text_encoder: TextEncoder, faiss_indexer: FaissIndexer):
        self.data_loader = data_loader
        self.text_encoder = text_encoder
        self.faiss_indexer = faiss_indexer

    def get_recommendations(self, book_name: str, k: int = 10) -> List[str]:
        try:
            df = self.data_loader.load_data()
            vectors = self.text_encoder.encode_texts(df['cleaned_description'].tolist())
            index = self.faiss_indexer.create_index(vectors)

            book_idx = df.index[df['Book'] == book_name].tolist()[0]
            book_vector = self.text_encoder.encode_texts([df.at[book_idx, 'cleaned_description']])
            distances, indices = self.faiss_indexer.search_index(index, book_vector, k)
            recommended_books = df.iloc[indices[0]][1:]
            return recommended_books['Book'].tolist()
        except (FileNotFoundError, ValueError, IndexError) as e:
            print(f"Error: {str(e)}")
            return []
