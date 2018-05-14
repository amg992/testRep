import numpy as np
import matplotlib.pyplot as plt

# Versuch 1

data = np.genfromtxt("v1.csv", delimiter=',', usecols=[3,4], skip_header=0)
# Messwerte aus dem Versuch als Diagramm:
fig, ax = plt.subplots()
ax.plot(data[:,0], data[:,1])
ax.set_title("Messwertdiagramm")
ax.set_xlabel("Zeit ins Sekunden")
ax.set_ylabel("Spannung in Volt")
plt.show()

# Einteilung der Messwerte in 3 Bereiche und ermitteln derer Minima
size = int(data[:,0].size / 3)
list = []
for i in range(0,3):
    min = np.argmin(data[i*size :(i+1)*size, 1])
    list.append(data[i*size+min , 0])

print(list) # Zeitpunkte der Minima im Graphen

a = list[1] - list[0] # Periode 1
b = list[2] - list[1] # Periode 2
p = (a+b)/2 # Periodendauer

print(p) # Periodendauer
f = 1/p
print(f) # Grundfrequenz

signaldauer = data[int(data.size/2)-1 , 0] - data[0 , 0] # Ende der x-Axis - Anfang
print(signaldauer) # 5 ms Signaldauer gerundet (5ms laut Messbild vom Oszilloskop)

anzahl_messungen = data.size/2

abtastfrequenz = (data.size/2) / signaldauer
print(abtastfrequenz) # Abtastfrequenz
m = data[:,0].size
print(m) # Anzahl der Abtastzeitpunkte (Signallänge M)

delta_t = 1/abtastfrequenz
print(delta_t) # Abtastintervall delta t


func = np.absolute(np.fft.fft(data[:,1]))
fig, ax = plt.subplots()
plt.xlim([-100, 12000])
ax.plot(range(2500)/(m*delta_t), func) # Amplitudenspektrum in Abhängigkeit der Frequenz
ax.set_title("Amplitudenspektrum")
ax.set_xlabel("Frequenz in Hertz")
ax.set_ylabel("Amplitude in Volt")
plt.show()

print(func[3]) # Amplitude der Grundfrequenz

# Versuch 2

# Phasenmessungen Lautsprecher 1
phase_mik1 = np.array([0.0054, 0.0045, 0.0003 + 1/300, 0.0004 + 1/400, 0.0003 + 1/500, 0.00028 + 1/700,0.00022 + 1/850,
0.00022 + 1/1000, 0.0002 + 1/1200, 0.00018 + 1/1500, 0.00018 + 1/1700, 0.00016 + 1/2000,
0.00012 + 1/3000, 0.000136 + 1/4000, 0.000152 + 1/5000, 0.000124 + 1/6000, 0.000036 + 1/10000])

# Amplitudenmessungen Lautsprecher 1
ampl_mik1 = np.array([0.03, 0.082, 0.054, 0.04, 0.028, 0.02, 0.018, 0.02, 0.02, 0.018, 0.016, 0.014, 0.018, 0.026,
0.016, 0.014, 0.012])

# Phasenmessungen Lautsprecher 2
phase_mik2 = np.array([0.0041, 0.00316, 0.00244, 0.002, 0.00012 + 1/500, 0.00028 + 1/700, 0.00025 + 1/850,
0.00024 + 1/1000, 0.00023 + 1/1200, 0.00016 + 1/1500, 0.00017 + 1/1700, 0.000148 + 1/2000,
0.000128 + 1/3000, 0.00014 + 1/4000, 0.000154 + 1/5000, 0.000084 + 1/6000, 0.000044 + 1/10000])

# Amplitudenmessungen Lautsprecher 2
ampl_mik2 = np.array([0.0076, 0.022, 0.032, 0.068, 0.108, 0.038, 0.0268, 0.0232, 0.0196, 0.0244, 0.02, 0.0204, 0.0248,
0.026, 0.0132, 0.0072, 0.012])

# Frequenzen
x_axis = np.array([100, 200, 300, 400, 500, 700, 850, 1000, 1200, 1500, 1700, 2000, 3000, 4000, 5000, 6000, 10000])


# Phasengang
fig, ax_phase = plt.subplots()
ax_phase.plot(x_axis, phase_mik1)
ax_phase.plot(x_axis, phase_mik2)
ax_phase.set_title("Phasengang")
ax_phase.set_xlabel("Frequenz in Hertz")
ax_phase.set_ylabel("Phasenverschiebung in Sekunden")
plt.show()
# Amplitudengang
fig, ax_amplitude = plt.subplots()
ax_amplitude.semilogx(x_axis, ampl_mik1)
ax_amplitude.semilogx(x_axis, ampl_mik2)
ax_amplitude.set_title("Amplitudengang")
ax_amplitude.set_xlabel("Frequenz in Hertz")
ax_amplitude.set_ylabel("Amplitude in Volt")
plt.show()

# Berechnung der Kreisfrequenz aus den Frequenzen
kreisfrequenz = x_axis / (2 * np.pi)

# Phasengang des Bode-Diagramms:
# Berechnung der Phasenwinkel
phasenwinkel1 = -phase_mik1 * x_axis * 2 * np.pi
phasenwinkel2 = -phase_mik2 * x_axis * 2 * np.pi
# Plotten
fig, ax_phase = plt.subplots()
ax_phase.set_title("Phasengang")
ax_phase.semilogx(kreisfrequenz, phasenwinkel1, label = "Lautsprecher 1")
ax_phase.semilogx(kreisfrequenz, phasenwinkel2, label = "Lautsprecher 2")
ax_phase.legend()
ax_phase.set_xlabel("Kreisfrequenz [1/s]")
ax_phase.set_ylabel("Phasenwinkel")
plt.show()

# Amplitudengang des Bode-Diagramms:
# Berechnung der Dezibelwerte
dezibel1 = 20 * np.log10(ampl_mik1)
dezibel2 = 20 * np.log10(ampl_mik2)
# Plotten
fig, ax_amplitude = plt.subplots()
ax_amplitude.set_title("Amplitudengang")
plt.semilogx(kreisfrequenz, dezibel1, label = "Lautsprecher 1")
plt.semilogx(kreisfrequenz, dezibel2, label = "Lautsprecher 2")
ax_amplitude.legend()
ax_amplitude.set_xlabel("Kreisfrequenz [1/s]")
ax_amplitude.set_ylabel("Dezibel")
plt.show()
