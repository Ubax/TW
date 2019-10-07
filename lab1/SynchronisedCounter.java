package lab1;

/**
 * Created on 07.10.19
 */
public class SynchronisedCounter {
    private int value = 0;

    public synchronized void increment(){
        this.value++;
    }

    public synchronized void decrement(){
        this.value--;
    }

    public int getValue() {
        return value;
    }
}
