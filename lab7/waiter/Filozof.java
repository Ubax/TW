import java.util.Random;

public class Filozof extends Thread {
    private int _licznik = 0;
    private Widelec left;
    private Widelec right;
    private int imie;
    private Waiter waiter;

    public Filozof(Widelec left, Widelec right, int imie, Waiter waiter){
        this.left = left;
        this.right = right;
        this.imie = imie;
        this.waiter = waiter;
    }

    public void run() {
        Random random = new Random();
        while (true) {

            while (!waiter.allow(this.left, this.right)) {
                mysl(Math.abs(random.nextLong()%2));
            }
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
