import java.util.Random;

public class Filozof extends Thread {
    private int _licznik = 0;
    private Widelec left;
    private Widelec right;
    private int imie;

    public Filozof(Widelec left, Widelec right, int imie){
        this.left = left;
        this.right = right;
        this.imie = imie;
    }

    public void run() {
        Random random = new Random();
        while (true) {

            this.left.podnies();
            this.right.podnies();

            // jedzenie
            ++_licznik;

            if (_licznik % 100 == 0) {
                System.out.println("Filozof: " + this.imie +
                        "jadlem " + _licznik + " razy");
            }
//            mysl(5L);
            this.left.odloz();
            this.right.odloz();
//            mysl(Math.abs(random.nextLong()%10));

            // koniec jedzenia
        }
    }

    private void mysl(Long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
