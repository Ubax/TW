package lab2;


/**
 * Created on 07.10.19
 */
public class Consumer implements Runnable {
    private Buffer buffer;
    public static int ILOSC=20;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for(int i = 0;  i < ILOSC;   i++) {
            System.out.println(buffer.take());
        }
    }
}