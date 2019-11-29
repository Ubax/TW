import java.util.concurrent.locks.ReentrantLock;

public class Waiter {

    private ReentrantLock lock = new ReentrantLock();

    public boolean allow(Widelec widelec1, Widelec widelec2) {
        lock.lock();
        try {
            if (widelec1.isAvaible() && widelec2.isAvaible()) {
                return true;
            } else {
                return false;
            }
        }finally {
            lock.unlock();
        }
    }
}
