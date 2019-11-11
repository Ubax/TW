package lab3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 23.10.19
 */
public class Kelner {
    Lock lock = new ReentrantLock();

    Stolik stolik;
    Map<Para, Integer> pary = new HashMap<>();
    Map<Para, Condition> paryCondition = new HashMap<>();
    Condition stolikCondition;

    public Kelner(Stolik stolik) {
        this.stolik = stolik;
        this.stolikCondition = lock.newCondition();
    }

    public void setPary(List<Para> pary){
        for (Para para : pary) {
            this.pary.put(para, 0);
            this.paryCondition.put(para, lock.newCondition());
        }
    }

    public Stolik chceStolik(Para para) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("Kelner> Status pary "+Integer.toString(pary.get(para)+1)+"/2");
            if (pary.get(para) == 0) {
                pary.put(para, 1);
                paryCondition.get(para).await();
            }
            else if (pary.get(para) == 1) {
                pary.put(para, 2);
                paryCondition.get(para).signal();
            }
            System.out.println("Kelner> Para czeka na zwolnienie stolika");
            while(stolik.getPara()!=para && stolik.czyJestZajety()){
                this.stolikCondition.await();
            }

            stolik.setPara(para);
            return stolik;
        } finally {
            lock.unlock();
        }
    }

    public void zwalniam(Para para) throws InterruptedException {
        lock.lock();
        try {
            if (pary.get(para) == 2) {
                pary.put(para, 3);
            }
            else if (pary.get(para) == 3) {
                pary.put(para, 0);
                this.stolik.zwolnij();
                this.stolikCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
