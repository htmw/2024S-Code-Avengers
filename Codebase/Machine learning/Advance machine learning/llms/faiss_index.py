import faiss
import numpy as np
from data_loader import BookDataLoader
from text_encoder import TextEncoder
from typing import Tuple

class FaissIndexer:
    def __init__(self, nlist: int = 50):
        self.nlist = nlist

    def create_index(self, vectors: np.ndarray) -> faiss.IndexIVFFlat:
        dimension = vectors.shape[1]
        quantizer = faiss.IndexFlatL2(dimension)
        index = faiss.IndexIVFFlat(quantizer, dimension, self.nlist, faiss.METRIC_L2)
        index.train(vectors)
        index.add(vectors)
        return index

    def search_index(self, index: faiss.IndexIVFFlat, query_vector: np.ndarray, k: int) -> Tuple[np.ndarray, np.ndarray]:
        distances, indices = index.search(query_vector, k + 1)
        return distances, indices

class BookIndexer:
    def __init__(self, data_loader: BookDataLoader, text_encoder: TextEncoder, faiss_indexer: FaissIndexer):
        self.data_loader = data_loader
        self.text_encoder = text_encoder
        self.faiss_indexer = faiss_indexer

    def index_books(self) -> None:
        try:
            df = self.data_loader.load_data()
            vectors = self.text_encoder.encode_texts(df['cleaned_description'].tolist())
            index = self.faiss_indexer.create_index(vectors)
            print("Created and trained Faiss index")
        except (FileNotFoundError, ValueError) as e:
            print(f"Error: {str(e)}")

def main() -> None:
    file_path = 'cleaned_goodreads_data.csv'
    data_loader = BookDataLoader(file_path)
    text_encoder = TextEncoder()
    faiss_indexer = FaissIndexer()
    book_indexer = BookIndexer(data_loader, text_encoder, faiss_indexer)
    book_indexer.index_books()

if __name__ == "__main__":
    main()
