package lab2;

/**
 * Created on 14.10.19
 */
public class CountingSemaphore implements Semaphore{
    private int numberOfTaken = 0;
    private int maxNumberOfSemaphores;

    public CountingSemaphore(int maxNumberOfSemaphores) {
        this.maxNumberOfSemaphores = maxNumberOfSemaphores;
    }

    public synchronized void V(){
        this.maxNumberOfSemaphores++;
        notifyAll();
    }

    public synchronized void P(){
        while(this.maxNumberOfSemaphores==0){
            try{
                wait();
            }catch (Exception e){

            }
        }
        this.maxNumberOfSemaphores--;
    }
}
