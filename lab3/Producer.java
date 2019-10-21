package lab3;


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
        System.out.println(prefix + " INIT");
        for(int i = 0;  i < ILOSC;   i++) {
            try {
                buffer.put(prefix + " message " + i);
            }catch (Exception e){
                System.out.println("Put error");
            }
        }
    }
}
