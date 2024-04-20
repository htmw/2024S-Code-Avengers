import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
import re
from nltk.stem import WordNetLemmatizer
from nltk.corpus import stopwords
import nltk

nltk.download('stopwords')
nltk.download('wordnet')

def preprocess_text(text):
    text = text.lower()
    text = re.sub(r'[^a-zA-Z\s]', '', text)
    stop_words = set(stopwords.words('english'))
    words = text.split()
    words = [word for word in words if word not in stop_words]
    lemmatizer = WordNetLemmatizer()
    lemmatized_words = [lemmatizer.lemmatize(word) for word in words]
    return ' '.join(lemmatized_words)

df = pd.read_csv('goodreads_data.csv')
df.dropna(subset=['Description'], inplace=True)
df.drop_duplicates(inplace=True)
df['cleaned_description'] = df['Description'].apply(preprocess_text)

tfidf_vectorizer = TfidfVectorizer(max_features=1000)
tfidf_matrix = tfidf_vectorizer.fit_transform(df['cleaned_description'])

cleaned_file_path = 'new_goodreads_data.csv'
df.to_csv(cleaned_file_path, index=False)
