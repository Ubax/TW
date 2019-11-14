package lab4.randomMessages.unfair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 13.11.19
 */
public class Buffer {
    private String elements[];
    int size;
    private int insertIndex = 0;
    private int readingIndex = 0;
    private int numberOfInsertedElements = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition decreaseNumberOfInsertedElementsCondition = lock.newCondition();
    private final Condition increaseNumberOfInsertedElementsCondition = lock.newCondition();

    private int finishedProducers;
    private List<Double> avgTimes = new ArrayList<>();

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
            increaseNumberOfInsertedElementsCondition.signalAll();
        }
    }

    public boolean shouldFinish(){
        return this.finishedProducers<=0;
    }

    public void clientFinished(double avgTime){
        this.avgTimes.add(avgTime);
    }

    public int next(int index) {
        return index < size - 1 ? index + 1 : 0;
    }

    public void insert(int numberOfElements, String prefix) {
        lock.lock();
        try {
            while (size - numberOfInsertedElements < numberOfElements) {
                decreaseNumberOfInsertedElementsCondition.await();
            }
            numberOfInsertedElements += numberOfElements;
            for (int i = 0; i < numberOfElements; i++) {
                elements[insertIndex] = prefix + Integer.toString(i);
                insertIndex = next(insertIndex);
            }
//            System.out.println("Buffer> Inserted " + numberOfElements + " messages");
            increaseNumberOfInsertedElementsCondition.signalAll();
        } catch (Exception e) {
            System.out.println("Insert exception");
        } finally {
            lock.unlock();
        }
    }

    public ArrayList<String> get(int numberOfElements) {
        ArrayList<String> arrayList = new ArrayList<>();
        lock.lock();
        try {
            while (!shouldFinish() && numberOfInsertedElements < numberOfElements) {
                increaseNumberOfInsertedElementsCondition.await();
            }
            for (int i = 0; i < numberOfElements; i++) {
                arrayList.add(elements[readingIndex]);
                readingIndex = next(readingIndex);
            }
            numberOfInsertedElements-=numberOfElements;
            decreaseNumberOfInsertedElementsCondition.signalAll();
        } catch (Exception e) {
            System.out.println("Reading exception");
        } finally {
            lock.unlock();
        }
        return arrayList;
    }
}
