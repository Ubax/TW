package lab3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 07.10.19
 */
public class Buffer {
    private String msg;
    private int count = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition isNotFull = lock.newCondition();
    private final Condition isNotEmpty = lock.newCondition();

    public boolean isFull() {
        return count >= 1;
    }

    public boolean isEmpty() {
        return count <= 0;
    }

    public void put(String msg) throws InterruptedException {
        lock.lock();
        try {
            while (isFull())
                isNotFull.await();
            this.msg=msg;
            ++count;
            isNotEmpty.signal();
        } finally {
            lock.unlock();
        }

    }

    public String take() throws InterruptedException {
        lock.lock();
        try {
            while (isEmpty())
                isNotEmpty.await();
            String x = msg;
            --count;
            isNotFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
