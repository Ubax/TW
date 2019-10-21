package lab3;


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
        System.out.println("INIT");
        for(int i = 0;  i < ILOSC;   i++) {
            try {
                System.out.println(buffer.take());
            }catch (Exception e){
                System.out.println("errro");
            }
        }
    }
}