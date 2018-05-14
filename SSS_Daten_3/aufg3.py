import numpy as np
from TekTDS2000 import *

scope = TekTDS2000()
x,y = scope.getData(1)
scope.saveCsv(filename="ch2freq10000klein.csv", ch=2)
scope.plot(2, filename='ch2freq10000klein.png')

scope.saveCsv(filename="freq10000klein.csv", ch=1)
scope.plot(1, filename='freq10000klein.png')


del scope