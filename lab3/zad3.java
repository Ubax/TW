package lab3;

import java.util.LinkedList;
import java.util.List;

/**
 * Created on 23.10.19
 */
public class zad3 {
    public static void main(String[] args) {
        Stolik stolik = new Stolik();
        Kelner kelner = new Kelner(stolik);
        List<Para> pary = new LinkedList<>();
        List<Osoba> osoby = new LinkedList<>();
        List<Thread> watki = new LinkedList<>();
        for(int i=4;i>=0;i--){
            Para para = new Para();
            pary.add(para);
            Osoba o1 = new Osoba(para, "Osoba "+Integer.toString(2*i), kelner);
            osoby.add(o1);
            watki.add(new Thread(o1));
            Osoba o2= new Osoba(para, "Osoba "+Integer.toString(2*i + 1), kelner);
            osoby.add(o1);
            watki.add(new Thread(o2));
        }
        kelner.setPary(pary);

        for(Thread t: watki){
            t.start();
        }

        try {
            for(Thread t: watki){
                t.join();
            }
        }catch(Exception e){
            System.out.println("error");
        }
    }
}
