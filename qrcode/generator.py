import pyqrcode
import base64
import time
import matplotlib.pyplot as plt
import matplotlib.image as mpimg
from PIL import Image
import qrcode


f = open("pan-tadeusz.txt", "rb")
data = f.read()

i = 0

plt.ion()
size = 30
chunkSize = 900
while(True):
    qr = qrcode.QRCode(
        version=30,
        error_correction=qrcode.constants.ERROR_CORRECT_H,
        box_size=10,
        border=4,
    )
    header = [str(i), str(size)]
    string = "".join([('|'.join(header[0:3])+"|"), ''.join(
        map(lambda x: chr(x), base64.b64encode(data[i*chunkSize:(i+1)*chunkSize])))])
    # print(string)
    # qr = pyqrcode.create(string, version=25)
    qr.add_data(string)
    qr.make(fit=True)
    png = qr.make_image(fill_color="black", back_color="white", scale=14)
    plt.imshow(png, cmap='binary')
    plt.draw()
    plt.pause(0.001)
    plt.clf()
    if i < size-1:
        i += 1
    else:
        i = 0


f.close()
