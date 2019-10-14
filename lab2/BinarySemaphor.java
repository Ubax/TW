package lab2;

/**
 * Created on 14.10.19
 */
public class BinarySemaphor implements Semaphore{
    private boolean isTaken;

    public synchronized void V(){
        this.isTaken=false;
        notifyAll();
    }

    public synchronized void P(){
        while (this.isTaken){
            try {
                wait();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        this.isTaken=true;
    }
}
