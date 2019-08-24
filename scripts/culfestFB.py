import pyrebase
from bs4 import BeautifulSoup
import requests
from config import config

firebase = pyrebase.initialize_app(config)
db = firebase.database()

stack = []

website_url = requests.get("https://www.facebook.com/pg/utk.nitjsr/posts").text
soup = BeautifulSoup(website_url, 'lxml')
url = "facebook.com/utk.nitjsr"

for div in soup.find_all('div',attrs={'class':'text_exposed_root'}):
    title = div.text
    id = div['id']
    if type(db.child("NIT Jamshedpur").child("fest").child(id).child("priority").get().val()) == type(1):
        break
    stack.append(id)
    stack.append(title)

priority = db.child("NIT Jamshedpur").child("fest").child("priority").get().val()
if type(priority) != type(1):
    priority = 1

while len(stack) != 0:
    title = stack.pop()
    id = stack.pop()
    db.child("NIT Jamshedpur").child("fest").child(id).child("title").set(title)
    db.child("NIT Jamshedpur").child("fest").child(id).child("priority").set(priority)
    db.child("NIT Jamshedpur").child("fest").child(id).child("url").set(url)
    db.child("NIT Jamshedpur").child("fest").child(id).child("name").set("Culfest")
    priority = priority + 1

db.child("NIT Jamshedpur").child("fest").child("priority").set(priority)
