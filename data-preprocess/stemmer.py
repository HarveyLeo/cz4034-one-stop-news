import json
from nltk.stem.porter import *
#from pprint import pprint

FILE_NAME = 'data.json'

stemmer = PorterStemmer()


with open(FILE_NAME, encoding="utf8") as data_file:    
    data = json.load(data_file)

for datum in data:
    datum['stemmed_message'] = ' '.join([stemmer.stem(word) for word in datum['message'].split(' ')])

print(json.dumps(data, indent=4, sort_keys=True))