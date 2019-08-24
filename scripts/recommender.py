import pyrebase
from config import config
import pandas as pd
import collections
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity

firebase = pyrebase.initialize_app(config)
db = firebase.database()

nits = []
nits.append("NIT Agartala");
nits.append("NIT Allahabad");
nits.append("NIT Andhra Pradesh");
nits.append("NIT Arunachal Pradesh");
nits.append("NIT Bhopal");
nits.append("NIT Calicut");
nits.append("NIT Delhi");
nits.append("NIT Durgapur");
nits.append("NIT Goa");
nits.append("NIT Hamirpur");
nits.append("NIT Jaipur");
nits.append("NIT Jalandhar");
nits.append("NIT Jamshedpur");
nits.append("NIT Karnataka");
nits.append("NIT Kurukshetra");
nits.append("NIT Manipur");
nits.append("NIT Meghalaya");
nits.append("NIT Mizoram");
nits.append("NIT Nagaland");
nits.append("NIT Nagpur");
nits.append("NIT Patna");
nits.append("NIT Puducherry");
nits.append("NIT Raipur");
nits.append("NIT Rourkela");
nits.append("NIT Sikkim");
nits.append("NIT Silchar");
nits.append("NIT Srinagar");
nits.append("NIT Surat");
nits.append("NIT Trichy");
nits.append("NIT Uttarakhand");
nits.append("NIT Warangal");

ids = []
data = []

for nit in nits:
    users = db.child("users").child(nit).get()
    for user in users.each():
        lists = user.val()['mList']
        string = ""
        for item in lists:
            item = item.replace(' ','')
            string += item
            string += ' '
        string = string.strip()
        id = user.key()
        ids.append(id)
        data.append(string)

count = CountVectorizer(analyzer='word', stop_words='english')
matrix = count.fit_transform(data)
similarity = cosine_similarity(matrix,matrix)

dict = {}
uid = []
i = 0
for id in ids:
    dict[id] = i
    uid.append(i)
    i += 1

for nit in nits:
    users = db.child("users").child(nit).get()
    for user in users.each():
        id = user.key()
        index = dict[id]
        score = list(enumerate(similarity[index]))
        score = sorted(score, key=lambda x: x[1], reverse=True)
        indices = [i[0] for i in score]
        top = indices[1:11]
        string = ""
        for ind in top:
            string += data[ind]
            string += ' '
        string = string.strip()
        lists = list(string.split(" "))
        counter = collections.Counter(lists)
        rec = counter.most_common(20)
        rec = [i[0] for i in rec]
        interest = list(data[index].split(" "))
        recommendation = [x for x in rec if x not in interest]
        if(len(recommendation) > 5):
            recommendation = recommendation[:5]
        db.child("users").child(nit).child(id).child("recommendation").set(recommendation)
