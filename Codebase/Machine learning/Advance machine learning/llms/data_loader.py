import pandas as pd
from typing import Union

class BookDataLoader:
    def __init__(self, file_path: str):
        self.file_path = file_path

    def load_data(self) -> pd.DataFrame:
        try:
            df = pd.read_csv(self.file_path)
        except FileNotFoundError:
            raise FileNotFoundError(f"File not found: {self.file_path}")

        if 'cleaned_description' not in df.columns:
            raise ValueError("'cleaned_description' column not found in the DataFrame.")

        df.dropna(subset=['cleaned_description'], inplace=True)
        return df

class BookRecommender:
    def __init__(self, data_loader: BookDataLoader):
        self.data_loader = data_loader

    def recommend_books(self) -> None:
        try:
            df = self.data_loader.load_data()
            print(f"Loaded {len(df)} books")
        except (FileNotFoundError, ValueError) as e:
            print(f"Error: {str(e)}")

def main() -> None:
    file_path = 'cleaned_goodreads_data.csv'
    data_loader = BookDataLoader(file_path)
    recommender = BookRecommender(data_loader)
    recommender.recommend_books()

if __name__ == "__main__":
    main()
