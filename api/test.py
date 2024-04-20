import requests
import argparse

def test_recommendations(base_url, book_name, n=10):
    url = f"{base_url}/recommendations/{book_name}?n={n}"
    response = requests.get(url)
    print(response.json())

def test_search(base_url, query):
    url = f"{base_url}/search?q={query}"
    response = requests.get(url)
    print(response.json())

parser = argparse.ArgumentParser(description='Test Flask API endpoints.')
parser.add_argument('--base_url', type=str, required=True, help='Base URL of the Flask app')
parser.add_argument('--test_type', type=str, required=True, choices=['recommendations', 'search'], help='Type of test to run')
parser.add_argument('--book_name', type=str, help='Book name for recommendations test')
parser.add_argument('--query', type=str, help='Query for search test')
parser.add_argument('--n', type=int, default=10, help='Number of recommendations to fetch')

args = parser.parse_args()

if args.test_type == 'recommendations':
    if not args.book_name:
        raise ValueError("Book name is required for recommendations test")
    test_recommendations(args.base_url, args.book_name, args.n)
elif args.test_type == 'search':
    if not args.query:
        raise ValueError("Query is required for search test")
    test_search(args.base_url, args.query)
