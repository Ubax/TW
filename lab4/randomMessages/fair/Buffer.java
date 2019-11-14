package lab4.randomMessages.fair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 13.11.19
 */
public class Buffer implements Runnable{
    private String elements[];
    int size;
    private int insertIndex = 0;
    private int readingIndex = 0;
    private int numberOfInsertedElements = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition newOrder = lock.newCondition();
    private List<Order> insertQueue = new LinkedList<>();
    private List<Order> readQueue = new LinkedList<>();

    public Buffer(int size) {
        this.size = size;
        elements = new String[size];
        for (int i = 0; i < size; i++) elements[i] = "";
    }

    public int next(int index) {
        return index < size - 1 ? index + 1 : 0;
    }

    private void processOrder(List<Order> queue, Order order){
        lock.lock();
        try {
            order.placeOrder();
            queue.add(order);
            newOrder.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void insert(InsertOrder order) {
        this.processOrder(insertQueue, order);
    }

    public void placeGet(ReadOrder order) {
        this.processOrder(readQueue, order);
    }

    @Override
    public void run() {
        boolean eventHappend;
        lock.lock();
        try {
            while (true) {
                eventHappend = false;
                if (insertQueue.size() > 0 && size - numberOfInsertedElements < insertQueue.get(0).getNumberOfElements()) {
                    InsertOrder order = (InsertOrder) insertQueue.get(0);
                    numberOfInsertedElements += order.numberOfElements;
                    for (int i = 0; i < order.numberOfElements; i++) {
                        elements[insertIndex] = order.getPrefix() + Integer.toString(i);
                        insertIndex = next(insertIndex);
                    }
                    System.out.println("Buffer> Inserted " + insertQueue.get(0).numberOfElements + " messages");
                    order.orderDone();
                    insertQueue.remove(0);
                    eventHappend = true;
                }
                if (readQueue.size() > 0 && size - numberOfInsertedElements < readQueue.get(0).getNumberOfElements()) {
                    ReadOrder order = (ReadOrder) readQueue.get(0);
                    ArrayList<String> arrayList = new ArrayList<>();
                    numberOfInsertedElements -= order.numberOfElements;
                    for (int i = 0; i < order.getNumberOfElements(); i++) {
                        arrayList.add(elements[readingIndex]);
                        readingIndex = next(readingIndex);
                    }
                    System.out.println("Buffer> Got " + readQueue.get(0).numberOfElements + " messages");
                    order.setData(arrayList);
                    order.orderDone();
                    insertQueue.remove(0);
                    eventHappend = true;
                }
                if(!eventHappend){
                    newOrder.await();
                }
            }
        }catch (Exception e){
            System.out.println("Buffer exception");
        }
        finally {
            lock.unlock();
        }
    }
}
