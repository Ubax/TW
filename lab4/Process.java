package lab4;

import lab2.BinarySemaphor;
import lab2.CountingSemaphore;
import lab2.Semaphore;

/**
 * Created on 11.11.19
 */
public abstract class Process implements Runnable {
    protected Buffer buffer;
    final protected Semaphore initSemaphore = new CountingSemaphore(0);
    protected int index = 0;

    public Process(Buffer buffer) {
        this.buffer = buffer;
    }

    public void init(){
        this.initSemaphore.V();
    }
}
