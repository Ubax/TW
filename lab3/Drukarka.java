package lab3;

import java.util.Random;
import java.util.concurrent.locks.Condition;

/**
 * Created on 21.10.19
 */
public class Drukarka {
    private final Condition condition;
    private String prefix;
    public Drukarka(Condition condition, String prefix){
        this.condition = condition;
        this.prefix = prefix;
    }

    public void zarezerwuj() throws InterruptedException{
            this.condition.await();

    }

    public void zwolnij() throws InterruptedException{
        this.condition.signal();
    }

    public void drukuj(){
        System.out.println(prefix + "> Drukuje...");
        Thread.sleep(new Random().nextInt(10)*1000);
        System.out.println(prefix + "> Skonczyłem ...");
    }
}
