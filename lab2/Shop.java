package lab2;

/**
 * Created on 14.10.19
 */
public class Shop {
    private Semaphore semaphore = new CountingSemaphore(3);

    public void takeCart(){
        semaphore.P();
    }

    public void returnCart(){
        semaphore.V();
    }
}
