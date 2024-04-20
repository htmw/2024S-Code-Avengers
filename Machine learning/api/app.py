from flask import Flask, jsonify, request
from flask_cors import CORS
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

app = Flask(__name__)
CORS(app)

df = pd.read_csv('cleaned_goodreads_data.csv')
df.dropna(subset=['cleaned_description'], inplace=True)
df.reset_index(drop=True, inplace=True)

tfidf = TfidfVectorizer()
mtx = tfidf.fit_transform(df['cleaned_description'])
sim = cosine_similarity(mtx)

idx_map = pd.Series(df.index, index=df['Book'].str.lower()).drop_duplicates()

@app.route('/recommendations/<book_name>', methods=['GET'])
def recommendations(book_name):
    n = request.args.get('n', default=10, type=int)
    book_name = book_name.lower()

    if book_name not in idx_map:
        return jsonify({"error": "Book not found"}), 404

    ix = idx_map[book_name]
    scores = list(enumerate(sim[ix]))
    scores = sorted(scores, key=lambda x: x[1], reverse=True)
    scores = scores[1:n + 1]

    book_ix = [i[0] for i in scores]
    recommended_books = df.iloc[book_ix, [1, 2, 3, 4, 5, 6]].to_dict('records')

    return jsonify(recommended_books)

@app.route('/search', methods=['GET'])
def search_books():
    query = request.args.get('q', '').lower()

    if not query:
        return jsonify([])

    search_results = df[df['Book'].str.lower().str.contains(query, case=False)]
    search_results = search_results[['Book', 'Author', 'Description', 'Genres', 'Avg_Rating', 'Num_Ratings']].to_dict('records')

    return jsonify(search_results)

if __name__ == '__main__':
    app.run(debug=True, port=5001)
