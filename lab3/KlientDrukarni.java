package lab3;


import java.util.Random;

/**
 * Created on 14.10.19
 */
public class KlientDrukarni implements Runnable {
    private String prefix;
    private Drukarka drukarka;
    private MonitorDrukarek monitorDrukarek;

    public KlientDrukarni(MonitorDrukarek monitorDrukarek, String prefix) {
        this.monitorDrukarek = monitorDrukarek;
        this.prefix = prefix;
    }

    @Override
    public void run() {
        while (1) {
            try {

                System.out.println(prefix + "> Czekam na zlecenie");
                Thread.sleep(new Random().nextInt(3) * 1000);
                System.out.println(prefix + "> Chce wydrukowac");
                drukarka = monitorDrukarek.zarezerwuj();
                System.out.println(prefix + "> Skonczyl drukowac");
                monitorDrukarek.zwolnij(drukarka);
            } catch (Exception e) {
                System.out.println("erro");
            }
        }
    }
}
