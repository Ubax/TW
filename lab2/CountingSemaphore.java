package lab2;

/**
 * Created on 14.10.19
 */
public class CountingSemaphore implements Semaphore{
    private int remainingResources = 0;

    public CountingSemaphore(int maxNumberOfSemaphores) {
        this.remainingResources = maxNumberOfSemaphores;
    }

    public synchronized void V(){
        this.remainingResources++;
        notifyAll();
    }

    public synchronized void P(){
        while(this.remainingResources==0){
            try{
                wait();
            }catch (Exception e){

            }
        }
        this.remainingResources--;
    }
}
