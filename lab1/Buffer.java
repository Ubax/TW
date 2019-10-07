package lab1;

/**
 * Created on 07.10.19
 */
public class Buffer {
    private String msg;
    private boolean isFull = false;

    public boolean isFull() {
        return isFull;
    }

    public synchronized void put(String msg){
        while (this.isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        this.isFull = true;
        this.msg=msg;
        notifyAll();
    }

    public synchronized String take(){
        while (!this.isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        String msg = this.msg;
        this.isFull = false;
        notifyAll();
        return msg;
    }
}
