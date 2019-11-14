package lab4.randomMessages.fair;

import lab2.BinarySemaphor;
import lab2.Semaphore;

import java.util.LinkedList;
import java.util.List;

/**
 * Created on 14.11.19
 */
public class AtomicQueue<T> {
    private List<T> queue = new LinkedList<>();
    private Semaphore semaphore = new BinarySemaphor();

    public void insert(T object){
        semaphore.P();
        queue.add(object);
        semaphore.V();
    }

    public T get(){
        semaphore.P();
        T ret = this.queue.size() > 0 ? this.queue.get(0) : null;
        semaphore.V();
        return ret;
    }

    public void remove(){
        semaphore.P();
        this.queue.remove(0);
        semaphore.V();
    }
}
