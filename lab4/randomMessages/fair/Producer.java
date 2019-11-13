package lab4.randomMessages.fair;

import java.util.Random;

/**
 * Created on 13.11.19
 */
public class Producer implements Runnable {
    private int M;
    private Buffer buffer;
    private Random random;

    public Producer(int m, Buffer buffer) {
        M = m;
        this.buffer = buffer;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                int numberOfMessages = random.nextInt(this.M - 1) + 1;
                String id = Integer.toString(random.nextInt(1000));
                System.out.println("Producer [" + id + "]> Want to send " + Integer.toString(numberOfMessages));
                this.buffer.insert(numberOfMessages, id + "##");
                Thread.sleep(random.nextInt(6) * 200 + 300);
            }
        } catch (Exception e) {
            System.out.println("Producer broke");
        }
    }
}
