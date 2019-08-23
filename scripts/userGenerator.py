import pyrebase
from config import config
import random

firebase = pyrebase.initialize_app(config)
db = firebase.database()

nit = []
nit.append("NIT Agartala");
nit.append("NIT Allahabad");
nit.append("NIT Andhra Pradesh");
nit.append("NIT Arunachal Pradesh");
nit.append("NIT Bhopal");
nit.append("NIT Calicut");
nit.append("NIT Delhi");
nit.append("NIT Durgapur");
nit.append("NIT Goa");
nit.append("NIT Hamirpur");
nit.append("NIT Jaipur");
nit.append("NIT Jalandhar");
nit.append("NIT Jamshedpur");
nit.append("NIT Karnataka");
nit.append("NIT Kurukshetra");
nit.append("NIT Manipur");
nit.append("NIT Meghalaya");
nit.append("NIT Mizoram");
nit.append("NIT Nagaland");
nit.append("NIT Nagpur");
nit.append("NIT Patna");
nit.append("NIT Puducherry");
nit.append("NIT Raipur");
nit.append("NIT Rourkela");
nit.append("NIT Sikkim");
nit.append("NIT Silchar");
nit.append("NIT Srinagar");
nit.append("NIT Surat");
nit.append("NIT Trichy");
nit.append("NIT Uttarakhand");
nit.append("NIT Warangal");

domains = []
domains.append("nita");
domains.append("mnnit");
domains.append("nitanp");
domains.append("nitap");
domains.append("manit");
domains.append("nitc");
domains.append("nitd");
domains.append("nitdgp");
domains.append("nitg");
domains.append("nith");
domains.append("mnit");
domains.append("nitj");
domains.append("nitjsr");
domains.append("nitk");
domains.append("nitkkr");
domains.append("nitmn");
domains.append("nitm");
domains.append("nitmz");
domains.append("nitn");
domains.append("vnit");
domains.append("nitp");
domains.append("nitpy");
domains.append("nitrr");
domains.append("nitrkl");
domains.append("nitskm");
domains.append("nits");
domains.append("nitsri");
domains.append("svnit");
domains.append("nitt");
domains.append("nituk");
domains.append("nitw");

with open('../datasets/firstName.txt', 'r') as f:
    first = [line.strip() for line in f]

with open('../datasets/lastName.txt', 'r') as f:
    last = [line.strip() for line in f]

l1 = len(first)
l2 = len(last)
for i in range(0,100):
    n = random.randrange(0,31,1)
    j = random.randrange(0,l1,1)
    k = random.randrange(0,l2,1)
    name = first[j] + " " + last[k]
    email = name.lower().replace(' ','')+"@"+domains[n]+".ac.in"
    data = {"mEmail":email,"mName":name}
    db.child("user").child(nit[n]).push(data)
