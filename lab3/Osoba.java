package lab3;

import java.util.Random;

/**
 * Created on 23.10.19
 */
public class Osoba implements Runnable {
    Para para;
    String imie;
    Kelner kelner;

    public Osoba(Para para, String imie, Kelner kelner) {
        this.para = para;
        this.imie = imie;
        this.kelner = kelner;
    }

    @Override
    public void run() {
        while (true){
            try {
                System.out.println(imie + "> Zalatwiam wlasne sprawy");
                Thread.sleep(new Random().nextInt(40) * 100 + 100);
                System.out.println(imie + "> Chce stolik");
                kelner.chceStolik(this.para);
                System.out.println(imie + "> Jem");
                Thread.sleep(new Random().nextInt(20) * 100 + 1000);
                System.out.println(imie + "> Zwalniam stolik");
                kelner.zwalniam(this.para);
            }catch (Exception e){
                System.out.println(imie + "> Nie zyje");
            }
        }
    }
}
