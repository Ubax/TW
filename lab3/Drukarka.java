package lab3;

import java.util.concurrent.locks.Condition;

/**
 * Created on 21.10.19
 */
public class Drukarka {
    private final Condition condition;
    public Drukarka(Condition condition){
        this.condition = condition;
    }

    public void zarezerwuj(){
        this.condition.await();
    }

    public void zwolnij(){
        this.condition.signal();
    }
}
