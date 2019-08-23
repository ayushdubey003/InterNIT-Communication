import pyrebase
from bs4 import BeautifulSoup
import requests
from config import config

firebase = pyrebase.initialize_app(config)
db = firebase.database()

stack = []

website_url = requests.get("http://nitjsr.ac.in/notice.php").text
soup = BeautifulSoup(website_url, 'lxml')
for div in soup.find_all('div',attrs={'class':'tab-pane fade in active'}):
    for a in div.find_all('a',attrs={'class':'foo'}):
        url = a['href'].replace('uploads/','')
        temp = url[:50]
        id = ""
        for i in temp:
            if i >= 'A' and i <= 'Z':
                id = id + i
            elif i >= 'a' and i <='z':
                id = id + i
        if type(db.child("NIT Jamshedpur").child("notice").child(id).child("priority").get().val()) == type(1):
            break
        title = a.text
        url = "nitjsr.ac.in/uploads/" + url.replace(' ','%20')
        stack.append(id)
        stack.append(title)
        stack.append(url)

priority = db.child("NIT Jamshedpur").child("notice").child("priority").get().val()
if type(priority) != type(1):
    priority = 1

while len(stack) != 0:
    url = stack.pop()
    title = stack.pop()
    id = stack.pop()
    db.child("NIT Jamshedpur").child("notice").child(id).child("title").set(title)
    db.child("NIT Jamshedpur").child("notice").child(id).child("url").set(url)
    db.child("NIT Jamshedpur").child("notice").child(id).child("priority").set(priority)
    priority = priority + 1

db.child("NIT Jamshedpur").child("notice").child("priority").set(priority)
