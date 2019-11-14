package lab4.randomMessages.fair;

import lab2.BinarySemaphor;
import lab2.Semaphore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 13.11.19
 */
public class Buffer {
    private String elements[];
    private int size;
    private int insertIndex = 0;
    private int readingIndex = 0;
    private int numberOfInsertedElements = 0;
    private int finishedProducers;
    private List<Double> avgTimes = new ArrayList<>();


    private final Semaphore eventSemaphore = new BinarySemaphor();
    private final Semaphore accessSemaphore = new BinarySemaphor();

    private AtomicQueue<InsertOrder> insertQueue = new AtomicQueue<>();
    private AtomicQueue<ReadOrder> readQueue = new AtomicQueue<>();

    public Buffer(int size, int numberOfProducers) {
        this.size = size;
        elements = new String[size];
        for (int i = 0; i < size; i++) elements[i] = "";
        this.finishedProducers=numberOfProducers;
    }

    public double getAvgTime(){
        return avgTimes.stream().reduce(0.0, (s, e)->s+e)/avgTimes.size();
    }

    public void producerFinished(double avgTime){
        this.avgTimes.add(avgTime);
        this.finishedProducers--;
        if(shouldFinish()){
            while (readQueue.get()!=null){
                readQueue.get().orderDone();
                readQueue.remove();
            }
            this.eventSemaphore.V();
        }
    }

    public void clientFinished(double avgTime){
        this.avgTimes.add(avgTime);
    }

    public boolean shouldFinish(){
        return this.finishedProducers<=0;
    }

    public Semaphore getEventSemaphore() {
        return eventSemaphore;
    }

    public int next(int index) {
        return index < size - 1 ? index + 1 : 0;
    }

    public InsertOrder getInsertOrder() {
        return insertQueue.get();
    }

    public ReadOrder getReadOrder() {
        return readQueue.get();
    }

    public void insert(int numberOfElements, String prefix){
//        System.out.println("Want to place insert order");
        InsertOrder order = new InsertOrder(numberOfElements, prefix);
        insertQueue.insert(order);
        eventSemaphore.V();
//        System.out.println("Place order inserted");
        order.placeOrder();
    }
    public ArrayList<String> get(int numberOfElements){
//        System.out.println("Want to place read order");
        ReadOrder order = new ReadOrder(numberOfElements);
        readQueue.insert(order);
        eventSemaphore.V();
//        System.out.println("Place order read");
        order.placeOrder();
        return order.getData();
    }

    public int getNumberOfInsertedElements() {
        return this.numberOfInsertedElements;
    }

    public int getNumberOfFreeElements() {
        return this.size - this.numberOfInsertedElements;
    }

    public void insert() {
        InsertOrder order = getInsertOrder();
        numberOfInsertedElements += order.numberOfElements;
        for (int i = 0; i < order.numberOfElements; i++) {
            elements[insertIndex] = order.getPrefix() + Integer.toString(i);
            insertIndex = next(insertIndex);
        }
//        System.out.println("Buffer> Inserted " + insertQueue.get().numberOfElements + " messages");
        order.orderDone();
        insertQueue.remove();
    }

    public void read() {
        ReadOrder order = getReadOrder();
        ArrayList<String> arrayList = new ArrayList<>();
        numberOfInsertedElements -= order.numberOfElements;
        for (int i = 0; i < order.getNumberOfElements(); i++) {
            arrayList.add(elements[readingIndex]);
            readingIndex = next(readingIndex);
        }
//        System.out.println("Buffer> Got " + readQueue.get().numberOfElements + " messages");
        order.setData(arrayList);
        order.orderDone();
        readQueue.remove();
    }
}
