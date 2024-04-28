from data_loader import BookDataLoader
from text_encoder import TextEncoder
from faiss_indexer import FaissIndexer
from recommender import BookRecommender

def main() -> None:
    file_path = 'cleaned_goodreads_data.csv'
    data_loader = BookDataLoader(file_path)
    text_encoder = TextEncoder()
    faiss_indexer = FaissIndexer()
    recommender = BookRecommender(data_loader, text_encoder, faiss_indexer)

    book_name = 'Harry Potter and the Philosopher's Stone (Harry Potter, #1)'
    recommendations = recommender.get_recommendations(book_name)
    print("Recommended Books:")
    print(recommendations)

if __name__ == "__main__":
    main()
