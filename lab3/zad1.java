package lab3;

/**
 * Created on 21.10.19
 */
public class zad1 {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Thread p1 = new Thread(new Producer(buffer, "p1"));
        Thread p2 = new Thread(new Producer(buffer,"p2"));
        Thread c1 = new Thread(new Consumer(buffer));
        Thread c2 = new Thread(new Consumer(buffer));

        p1.start();
        p2.start();
        c1.start();
        c2.start();

        try {
            p1.join();
            p2.join();
            c1.join();
            c2.join();
        }catch(Exception e){
            System.out.println("error");
        }
    }
}
