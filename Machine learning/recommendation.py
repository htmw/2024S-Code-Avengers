import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

df = pd.read_csv('cleaned_goodreads_data.csv')
df.dropna(subset=['cleaned_description'], inplace=True)

tfidf = TfidfVectorizer()
mtx = tfidf.fit_transform(df['cleaned_description'])

sim = cosine_similarity(mtx)

idx_map = pd.Series(df.index, index=df['Book']).drop_duplicates()

def rec(book, n=10):
    ix = idx_map[book]
    scores = list(enumerate(sim[ix]))
    scores = sorted(scores, key=lambda x: x[1], reverse=True)
    scores = scores[1:n + 1]
    book_ix = [i[0] for i in scores]
    return df['Book'].iloc[book_ix]

recs = rec('Harry Potter and the Philosopher’s Stone (Harry Potter, #1)')
print(recs)
