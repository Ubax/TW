package lab1;


import java.util.Date;

/**
 * Created on 07.10.19
 */
public class Producer implements Runnable {
    private Buffer buffer;
    public static int ILOSC=20;
    private String prefix;

    public Producer(Buffer buffer, String prefix) {
        this.buffer = buffer;
        this.prefix = prefix;
    }

    public void run() {
        for(int i = 0;  i < ILOSC;   i++) {
            buffer.put(Long.toString(System.nanoTime()) + "\t"+prefix + "message "+i);
        }
    }
}
