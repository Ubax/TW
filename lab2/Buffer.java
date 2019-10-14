package lab2;

/**
 * Created on 07.10.19
 */
public class Buffer {
    private String msg;
    private boolean isFull = false;

    private Semaphore semaphore = new BinarySemaphor();

    public boolean isFull() {
        return isFull;
    }

    public void put(String msg){
        semaphore.P();
        while(isFull){
            semaphore.V();
            semaphore.P();
        }
        this.isFull = true;
        this.msg=msg;
        semaphore.V();
    }

    public String take(){
        semaphore.P();
        while (!isFull){
            semaphore.V();
            semaphore.P();
        }
        String msg = this.msg;
        this.isFull = false;
        semaphore.V();
        return msg;
    }
}
