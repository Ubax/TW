package lab1;

/**
 * Created on 07.10.19
 */
public class zad2 {

    public static void main(String[] args) {
        SynchronisedCounter counter = new SynchronisedCounter();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) counter.decrement();
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) counter.increment();
        });

        System.out.println("Starting value: " + Integer.toString(counter.getValue()));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (Exception e) {
            System.out.println("error");
        }
        System.out.println("End value: " + Integer.toString(counter.getValue()));
    }
}
