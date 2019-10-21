package lab3;

/**
 * Created on 21.10.19
 */
public class zad2 {
    public static void main(String[] args) {
        MonitorDrukarek monitorDrukarek = new MonitorDrukarek(3);
        Thread c1 = new Thread(new KlientDrukarni(monitorDrukarek, "Client 1\t"));
        Thread c2 = new Thread(new KlientDrukarni(monitorDrukarek, "Client 2\t"));
        Thread c3 = new Thread(new KlientDrukarni(monitorDrukarek, "Client 3\t"));
        Thread c4 = new Thread(new KlientDrukarni(monitorDrukarek, "Client 4\t"));
        Thread c5 = new Thread(new KlientDrukarni(monitorDrukarek, "Client 5\t"));
        Thread c6 = new Thread(new KlientDrukarni(monitorDrukarek, "Client 6\t"));
        Thread c7 = new Thread(new KlientDrukarni(monitorDrukarek, "Client 7\t"));
        Thread c8 = new Thread(new KlientDrukarni(monitorDrukarek, "Client 8\t"));

        c1.start();
        c2.start();
        c3.start();
        c4.start();
//        c5.start();
//        c6.start();
//        c7.start();
//        c8.start();

        try {
            c1.join();
            c2.join();
            c3.join();
            c4.join();
//            c5.join();
//            c6.join();
//            c7.join();
//            c8.join();
        }catch(Exception e){
            System.out.println("error");
        }
    }
}
