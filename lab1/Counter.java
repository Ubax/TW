package lab1;

/**
 * Created on 07.10.19
 */
public class Counter {
    private int value = 0;

    public void increment(){
        this.value++;
    }

    public void decrement(){
        this.value--;
    }

    public int getValue() {
        return value;
    }
}
