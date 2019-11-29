import java.util.concurrent.locks.ReentrantLock;

public class Widelec {
    private ReentrantLock lock = new ReentrantLock();

    public void podnies() {
        this.lock.lock();
    }
    public void odloz() {
        this.lock.unlock();
    }
    public boolean isAvaible(){
        return  !this.lock.isLocked();
    }
}
