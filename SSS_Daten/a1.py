from TekTDS2000 import *
import numpy as np
v = np.genfromtxt("dina4.csv",delimiter=",")
print(v[1000:,3])
st = np.std(v[1000:,4],dtype=np.float64)
mt = np.mean(v[1000:,4],dtype=np.float64)
print(st)
print(mt)