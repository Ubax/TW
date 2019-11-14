package lab4.randomMessages.unfair;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created on 13.11.19
 */
public class Client implements Runnable{

    private int M;
    private Buffer buffer;
    private Random random;
    private long sumTime;
    private int numberOfCycles=0;

    public Client(int m, Buffer buffer) {
        M = m;
        this.buffer = buffer;
        this.random = new Random();
    }

    @Override
    public void run() {
        long start;
        try {
            while (!this.buffer.shouldFinish()) {
                start=System.nanoTime();
                this.buffer.get(random.nextInt(this.M - 1) + 1);
                sumTime+=System.nanoTime()-start;
                numberOfCycles++;
//                System.out.println(list);
//                Thread.sleep(10);
            }
            this.buffer.clientFinished((double) sumTime/ (double)numberOfCycles);
            System.out.println("Client> Finished ");
        }catch (Exception e){
            System.out.println("Client broke");
            e.printStackTrace();
        }
    }
}
