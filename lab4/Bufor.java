package lab4;

import lab2.BinarySemaphor;
import lab2.Semaphore;

import java.util.ArrayList;

/**
 * Created on 11.11.19
 */
public class Bufor {
    private static int SIZE = 100;
    private Cell[] data = new Cell[SIZE];
    private final Semaphore writing = new BinarySemaphor();
    private final Semaphore reading = new BinarySemaphor();
    private int index = 0;

    public void insert(String data){
        writing.P();
        this.data[index].insert(data);
        index++;
        writing.V();
    }

    public int next(int i){
        return i>=SIZE ? 0 : i+1;
    }

    public void startProcessing(int i){
        this.data[i].startProcessing();
    }

    public void endProcessing(int i){
        this.data[i].endProcessing();
    }

    public String get(int i){
        reading.P();
        String v = this.data[i].get();
        reading.V();
        return v;
    }

    public void clear(int i){
        reading.P();
        writing.P();
        this.data[i].clear();
        writing.V();
        reading.V();
    }
}
