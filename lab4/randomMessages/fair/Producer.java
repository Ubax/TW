package lab4.randomMessages.fair;


import java.util.Random;

/**
 * Created on 13.11.19
 */
public class Producer implements Runnable {
    private int M;
    private Buffer buffer;
    private Random random;
    private long sumTime;
    private final int numberOfCycles=50;

    public Producer(int m, Buffer buffer) {
        M = m;
        this.buffer = buffer;
        this.random = new Random();
    }

    @Override
    public void run() {
        long start;
        try {
            for(int i=0;i<numberOfCycles;i++){
//                if(i%100==0) System.out.println("Producer");
                int numberOfMessages = random.nextInt(this.M - 1) + 1;
                String id = Integer.toString(random.nextInt(1000));
//                System.out.println("Producer [" + id + "]> Want to send " + Integer.toString(numberOfMessages));
                start=System.nanoTime();
                this.buffer.insert(numberOfMessages, id + "##");
                sumTime+=System.nanoTime()-start;
//                Thread.sleep(random.nextInt(6) * 200 + 300);
            }
            this.buffer.producerFinished((double) sumTime/ (double)numberOfCycles);
            System.out.println("Producer> Finished");
        } catch (Exception e) {
            System.out.println("Producer broke");
            e.printStackTrace();
        }
    }
}
