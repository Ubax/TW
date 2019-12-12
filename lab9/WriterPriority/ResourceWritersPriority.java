package lab9.WriterPriority;

import lab9.IResource;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResourceWritersPriority implements IResource {
    private Lock lock = new ReentrantLock();
    private Condition okToRead = lock.newCondition();
    private Condition okToWrite = lock.newCondition();

    private int waitingReaders = 0;
    private int waitingWriters = 0;
    private int activeReaders = 0;
    private int activeWriters = 0;

    public void getReadingAccess() throws InterruptedException {
        try {
            lock.lock();
            while (activeWriters + waitingWriters > 0) {
                waitingReaders++;
                okToRead.await();
                waitingReaders--;
            }
            activeReaders++;
        } finally {
            lock.unlock();
        }
    }

    public void getWritingAccess() throws InterruptedException {
        try {
            lock.lock();
            if (activeReaders + activeWriters > 0) {
                waitingWriters++;
                okToWrite.await();
                waitingWriters--;
            }
        } finally {
            lock.unlock();
        }
    }

    public void releaseReadingAccess() throws InterruptedException {
        try {
            lock.lock();
            activeReaders--;
            if (activeReaders == 0 && waitingWriters > 0) {
                okToWrite.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void releaseWritingAccess() {
        try {
            lock.lock();
            if (waitingWriters > 0) {
                okToWrite.signal();
            } else if (waitingReaders > 0) {
                okToRead.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
