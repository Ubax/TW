package lab4;


import lab2.BinarySemaphor;
import lab2.Semaphore;

/**
 * Created on 11.11.19
 */
public class Cell {
    private String data;
    private boolean isEmpty=true;
    private final Semaphore empty = new BinarySemaphor();
    private final Semaphore beingProcessed = new BinarySemaphor();

    public String get(){
        return this.data;
    }

    public void startProcessing(){
        this.beingProcessed.P();
    }

    public void endProcessing(){
        this.beingProcessed.V();
    }

    public void waitTillEmpty(){
        this.empty.P();
    }

    public void insert(String data){
        this.data=data;
        this.isEmpty=false;
    }

    public void edit(String data){
        this.data=data;
    }

    public void clear(){
        this.data="";
        this.isEmpty=true;
        this.empty.V();
    }
}
