import matplotlib.pyplot as plt
import csv
import numpy as np

def processData(data):
    data.sort()
    x = []
    y = []
    lastX=data[0][0]
    sum=data[0][1]
    i=1
    max=0
    for point in data:
        if point[0]!=lastX:
            x.append(lastX)
            y.append(sum/i)
            if sum/i>max:
                max=sum/i
            sum=point[1]
            i=1
            lastX=point[0]
        else:
            sum=sum+point[1]
    x=[x[i] for i in range(len(x)) if y[i]<max*0.9]
    y=[_y for _y in y if _y<max*0.9]
    return (x,y)

def drawPlot(data_fair, data_unfair):
    xf,yf=processData(data_fair)
    xu,yu=processData(data_unfair)

    plt.plot(xf,yf, 'ro', label='Fair')
    plt.plot(xu,yu, 'bo', label='Unfair',)
    plt.xlabel('x')
    plt.ylabel('y')
    plt.title('Comparision of two buffer strategies')
    plt.legend()
    plt.show()

data_fair = []
with open('client-fair.csv','r') as csvfile:
    plots = csv.reader(csvfile, delimiter=',')
    for row in plots:
        data_fair.append((int(row[0]), int(row[1])))
data_fair = list(filter(lambda x: x[0]%50==0, data_fair))

data_unfair = []
with open('client-unfair.csv','r') as csvfile:
    plots = csv.reader(csvfile, delimiter=',')
    for row in plots:
        data_unfair.append((int(row[0]), int(row[1])))
data_unfair = list(filter(lambda x: x[0]%100==0, data_unfair))

drawPlot(data_fair, data_unfair)