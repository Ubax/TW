package lab4.randomMessages.fair;

import lab2.BinarySemaphor;
import lab2.Semaphore;

/**
 * Created on 13.11.19
 */
public abstract class Order {
    protected int numberOfElements;
    protected final Semaphore semaphore = new BinarySemaphor();

    public Order(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public void placeOrder(){
        semaphore.P();
    }

    public void orderDone(){
        semaphore.V();
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }
}
