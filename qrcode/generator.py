import pyqrcode
import base64
import time
import matplotlib.pyplot as plt
import matplotlib.image as mpimg

f = open("pan-tadeusz.txt", "rb")
data = f.read()

i = 0

plt.ion()
size = 30
while(True):
    header = [str(i), str(size)]
    string = ('|'.join(header[0:3])+"|")+''.join(
        map(lambda x: chr(x), base64.b64encode(data[i*100:(i+1)*100])))
    print(string)
    qr = pyqrcode.create(string, version=25)
    qr.png('data'+str(i), scale=15)
    img = mpimg.imread('data'+str(i))
    plt.imshow(img)
    plt.draw()
    plt.pause(0.001)
    plt.clf()
    if i < size-1:
        i += 1
    else:
        i = 0

f.close()
