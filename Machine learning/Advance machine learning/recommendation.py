import numpy as np
import pandas as pd
import faiss
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.preprocessing import normalize

def load_data(file_path):
    df = pd.read_csv(file_path)
    df.dropna(subset=['cleaned_description'], inplace=True)
    return df

def vectorize_texts(texts):
    tfidf = TfidfVectorizer(max_features=10000, stop_words='english', min_df=5, max_df=0.7)
    X_tfidf = tfidf.fit_transform(texts)
    X_normalized = normalize(X_tfidf, norm='l2', axis=1)
    return X_normalized.toarray(), tfidf

def create_faiss_index(vectors, nlist=50):
    dimension = vectors.shape[1]
    quantizer = faiss.IndexFlatL2(dimension)
    index = faiss.IndexIVFFlat(quantizer, dimension, nlist, faiss.METRIC_L2)
    index.train(vectors)
    index.add(vectors)
    return index

def search_index(index, query_vector, k):
    distances, indices = index.search(query_vector, k+1)
    return distances, indices

def get_recommendations(book_name, df, tfidf, index, k=10):
    book_idx = df.index[df['Book'] == book_name].tolist()[0]
    book_vector = tfidf.transform([df.at[book_idx, 'cleaned_description']]).toarray()
    distances, indices = search_index(index, book_vector.astype('float32'), k)
    recommended_books = df.iloc[indices[0]][1:]
    return recommended_books['Book']

def main():
    df = load_data('cleaned_goodreads_data.csv')
    vectors, tfidf = vectorize_texts(df['cleaned_description'])
    index = create_faiss_index(vectors.astype('float32'))
    book_name = 'Harry Potter and the Philosopher’s Stone (Harry Potter, #1)'
    recommendations = get_recommendations(book_name, df, tfidf, index)
    print("Recommended Books:")
    print(recommendations)

if __name__ == "__main__":
    main()

