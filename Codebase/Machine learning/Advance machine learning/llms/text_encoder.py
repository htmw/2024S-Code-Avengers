from langchain.llms import OpenAI
from langchain.embeddings import OpenAIEmbeddings
from data_loader import BookDataLoader
from typing import List

class TextEncoder:
    def __init__(self, model_name: str = "text-embedding-ada-002"):
        self.model_name = model_name
        self.embeddings = OpenAIEmbeddings(model=model_name)

    def encode_texts(self, texts: List[str]) -> List[List[float]]:
        vectors = self.embeddings.embed_documents(texts)
        return vectors

class BookEncoder:
    def __init__(self, data_loader: BookDataLoader, text_encoder: TextEncoder):
        self.data_loader = data_loader
        self.text_encoder = text_encoder

    def encode_book_descriptions(self) -> None:
        try:
            df = self.data_loader.load_data()
            vectors = self.text_encoder.encode_texts(df['cleaned_description'].tolist())
            print(f"Encoded {len(vectors)} book descriptions")
        except (FileNotFoundError, ValueError) as e:
            print(f"Error: {str(e)}")

def main() -> None:
    file_path = 'cleaned_goodreads_data.csv'
    data_loader = BookDataLoader(file_path)
    text_encoder = TextEncoder()
    book_encoder = BookEncoder(data_loader, text_encoder)
    book_encoder.encode_book_descriptions()

if __name__ == "__main__":
    main()
