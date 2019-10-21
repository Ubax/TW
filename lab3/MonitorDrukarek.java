package lab3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 21.10.19
 */
public class MonitorDrukarek {
    private final Lock lock = new ReentrantLock();
    private List<Drukarka> drukarki = new ArrayList<>();
    int id = 0;

    public MonitorDrukarek(int liczba) {
        for (int i = 0; i < liczba; i++) {
            drukarki.add(new Drukarka(lock.newCondition()));
        }
    }

    public Drukarka zarezerwuj() {
        lock.lock();
        try {
            Drukarka drukarka = this.drukarki.get(id++);
            if (id >= drukarki.size()) {
                id = 0;
            }
            drukarka.zarezerwuj();
            return drukarka;
        }finally {
            lock.unlock();
        }
    }

    public void zwolnij(Drukarka drukarka) {
        lock.lock();
        try {
        drukarka.zwolnij();
        }finally {
            lock.unlock();
        }
    }
}
