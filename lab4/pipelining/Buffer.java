package lab4.pipelining;

import lab2.BinarySemaphor;
import lab2.Semaphore;

/**
 * Created on 11.11.19
 */
public class Buffer {
    private int size;
    private Cell[] data;
    private final Semaphore writing = new BinarySemaphor();
    private final Semaphore reading = new BinarySemaphor();
    private int index = 0;

    public Buffer(int size) {
        this.size = size;
        data = new Cell[size];
        for (int i = 0; i < size; i++) {
            this.data[i] = new Cell();
        }
    }

    public void insert(String data) {
        this.data[index].waitTillEmpty();
        writing.P();
        this.data[index].insert(data);
        index=this.next(index);
        writing.V();
    }

    public int next(int i) {
        return i >= size - 1 ? 0 : i + 1;
    }

    public void startProcessing(int i) {
        this.data[i].startProcessing();
    }

    public void endProcessing(int i) {
        this.data[i].endProcessing();
    }

    public String get(int i) {
        reading.P();
        String v = this.data[i].get();
        reading.V();
        return v;
    }

    public void edit(int index, String data) {
        reading.P();
        this.data[index].edit(data);
        reading.V();
    }

    public void clear(int i) {
        reading.P();
        writing.P();
        this.data[i].clear();
        writing.V();
        reading.V();
    }
}
