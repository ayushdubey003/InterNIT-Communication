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

interests = []
interests.append("Ground Vehicle Systems");
interests.append("Manufacturing");
interests.append("Mechanical Design");
interests.append("Transportation System");
interests.append("System Dynamics and Control");
interests.append("Aerospace Engineering");
interests.append("Off Road Vehicle");
interests.append("Formula One Vehicle");
interests.append("Material Science");
interests.append("Competitive Coding");
interests.append("Machine Learning");
interests.append("Web Development");
interests.append("App Development");
interests.append("Blockchain");
interests.append("Software Development");
interests.append("JAVA");
interests.append("Python");
interests.append("C++");
interests.append("Microprocessor");
interests.append("Database Management System");
interests.append("Circuit Theory");
interests.append("Micro Electronics");
interests.append("VLSI");
interests.append("Integrated Circuits");
interests.append("Telecommunication");
interests.append("Nanotechnology");
interests.append("Digital Signal Processing");
interests.append("Analog Signal Processing");
interests.append("Embedded Systems");
interests.append("Digital Signal Processing");
interests.append("Analog Signal Processing");
interests.append("Industrial Instrumentation");
interests.append("Power System");
interests.append("Microprocessor");
interests.append("Communication System");
interests.append("Thermodynamics");
interests.append("Physics of Materials");
interests.append("Material Science");
interests.append("Extractive Metallurgy");
interests.append("Iron Making");
interests.append("X-ray Diffraction");
interests.append("Steel Making");
interests.append("Construction");
interests.append("Planning and Management");
interests.append("Concrete Design");
interests.append("Structural Analysis");
interests.append("Geotechnical Engineering");
interests.append("Survey");
interests.append("Hydraulics");
interests.append("Structural Mechanics");

with open('../datasets/firstName.txt', 'r') as f:
    first = [line.strip() for line in f]

with open('../datasets/lastName.txt', 'r') as f:
    last = [line.strip() for line in f]

l1 = len(first)
l2 = len(last)
l3 = len(interests)
for i in range(0,1000):
    n = random.randrange(0,31,1)
    j = random.randrange(0,l1,1)
    k = random.randrange(0,l2,1)
    name = first[j] + " " + last[k]
    k = random.randrange(1,11,1)
    rand_numbers = random.sample(range(0, l3), k)
    intr = {}
    k = 0
    for j in rand_numbers:
        intr[k] = interests[j]
        k = k + 1
    email = name.lower().replace(' ','')+"@"+domains[n]+".ac.in"
    data = {"mEmail":email,"mName":name,"mList":intr}
    db.child("users").child(nit[n]).push(data)
