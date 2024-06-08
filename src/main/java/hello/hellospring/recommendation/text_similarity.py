import spacy
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
# pip install spacy
# python -m spacy download en_core_web_md
# pip install scikit-learn

# Load the spaCy model
nlp = spacy.load('en_core_web_md')

def preprocess(text):
    doc = nlp(text)
    return ' '.join([token.lemma_ for token in doc if not token.is_stop and not token.is_punct])

def calculate_similarity(text1, text2):
    # Preprocess the texts
    text1_processed = preprocess(text1)
    text2_processed = preprocess(text2)

    # Use TF-IDF vectorizer to transform texts
    vectorizer = TfidfVectorizer().fit_transform([text1_processed, text2_processed])
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
