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


    public Client(int m, Buffer buffer) {
        M = m;
        this.buffer = buffer;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                ArrayList<String> list = this.buffer.get(random.nextInt(this.M - 1) + 1);
                System.out.println(list);
                Thread.sleep(10);
            }
        }catch (Exception e){
            System.out.println("Client broke");
        }
    }
}
