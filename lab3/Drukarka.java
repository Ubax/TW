package lab3;

import java.util.Random;
import java.util.concurrent.locks.Condition;

/**
 * Created on 21.10.19
 */
public class Drukarka {
    private final Condition condition;
    private String prefix;
    private boolean isBeingUsed = false;

    public Drukarka(Condition condition, String prefix) {
        this.condition = condition;
        this.prefix = prefix;
    }

    public void zarezerwuj() throws InterruptedException {
        while (isBeingUsed) this.condition.await();
        this.isBeingUsed = true;
    }

    public void zwolnij() throws InterruptedException {
        this.isBeingUsed=false;
        this.condition.signal();
    }

    public void drukuj() throws InterruptedException {
        System.out.println(prefix + "> Drukuje...");
        Thread.sleep(new Random().nextInt(10) * 1000);
        System.out.println(prefix + "> Skonczyłem ...");
    }
}
