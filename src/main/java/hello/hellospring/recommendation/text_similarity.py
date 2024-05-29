import spacy
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

# Load the spaCy model
nlp = spacy.load('en_core_web_md')

def calculate_similarity(text1, text2):
    # Convert texts to spaCy documents
    doc1 = nlp(text1)
    doc2 = nlp(text2)

    # Use TF-IDF vectorizer to transform texts
    vectorizer = TfidfVectorizer().fit_transform([text1, text2])
    vectors = vectorizer.toarray()

    # Calculate cosine similarity
    similarity = cosine_similarity(vectors)

    return similarity[0][1]

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 3:
        print("Usage: python text_similarity.py <text1> <text2>")
        sys.exit(1)

    text1 = sys.argv[1]
    text2 = sys.argv[2]
    similarity = calculate_similarity(text1, text2)
    print(similarity)