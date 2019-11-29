import threading
import random
import time

# Dining philosophers, 5 Phillies with 5 forks. Must have two forks to eat.
#
# Deadlock is avoided by never waiting for a fork while holding a fork (locked)
# Procedure is to do block while waiting to get first fork, and a nonblocking
# acquire of second fork.  If failed to get second fork, release first fork,
# swap which fork is first and which is second and retry until getting both.
#
# See discussion page note about 'live lock'.


class Philosopher(threading.Thread):
    running = True

    def __init__(self, name, leftFork, rightFork):
        threading.Thread.__init__(self)
        self.name = name
        self.leftFork = leftFork
        self.rightFork = rightFork

    def _log(self, msg):
        print("{}: {}".format(self.name, msg))

    def run(self):
        while(self.running):
            self._log("Thinking")
            time.sleep(random.randrange(1,100)/100)
            self._log("Want to eat")
            self.dine()

    def dine(self):
        fork1, fork2 = self.leftFork, self.rightFork

        fork1.acquire(True)
        self._log("Got left")
        fork2.acquire(True)
        self._log("Got right")
        self.dining()
        fork1.release()
        fork2.release()

    def dining(self):
        self._log("Is eating")
        time.sleep(random.randrange(1,100)/100 + 200)
        self._log("Ate")


def DiningPhilosophers():
    forks = [threading.Lock() for n in range(5)]
    philosopherNames = ('Aristotle', 'Kant', 'Buddha', 'Marx', 'Russel')

    philosophers = [Philosopher(philosopherNames[i], forks[i % 5], forks[(i+1) % 5])
                    for i in range(5)]

    random.seed(507129)
    Philosopher.running = True
    for p in philosophers:
        p.start()
    time.sleep(100)
    Philosopher.running = False
    print("Now we're finishing.")


DiningPhilosophers()
