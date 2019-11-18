package lab4.randomMessages.fair;

import lab4.randomMessages.Data;
import lab4.randomMessages.DataWriter;

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
    private final Condition firstInsert = lock.newCondition();
    private final Condition firstRead = lock.newCondition();
    private boolean isFirstInsert = false;
    private boolean isFirstRead = false;

    private DataWriter dataClientWriter = new DataWriter("client-fair.csv");
    private DataWriter dataProducerWriter = new DataWriter("producer-fair.csv");

    private final int numberOfCyclesToWrite;

    private List<Long> clientTimes = new ArrayList<>();
    private List<Long> producerTime = new ArrayList<>();

    public Buffer(int size, int numberOfCyclesToWrite) {
        this.size = size;
        elements = new String[size];
        for (int i = 0; i < size; i++) elements[i] = "";
        this.numberOfCyclesToWrite = numberOfCyclesToWrite;
    }

//    public double getAvgClientTime() {
//        return clientTimes.stream().reduce(0.0, (s, e) -> s + e) / clientTimes.size();
//    }
//
//    public double getAvgProducerTime() {
//        return producerTime.stream().reduce(0.0, (s, e) -> s + e) / producerTime.size();
//    }

    public void addProducerTime(long avgTime, int size) {
        this.producerTime.add(avgTime);
        if (this.producerTime.size() > numberOfCyclesToWrite) dataProducerWriter.write(new Data(avgTime, size));
        if (this.producerTime.size() % numberOfCyclesToWrite == 0) {
            dataProducerWriter.save();
            System.out.println("Producer saved");
        }
    }

    public void addClientTime(long avgTime, int size) {
        this.clientTimes.add(avgTime);
        if (this.clientTimes.size() > numberOfCyclesToWrite) dataClientWriter.write(new Data(avgTime, size));
        if (this.clientTimes.size() % numberOfCyclesToWrite == 0) {
            dataClientWriter.save();
            System.out.println("Client saved");
        }
    }


    public int next(int index) {
        return index < size - 1 ? index + 1 : 0;
    }

    public void insert(int numberOfElements, String prefix) {
        long start = System.nanoTime();
        lock.lock();
        try {
            if (isFirstInsert) {
                this.firstInsert.await();
            }
            isFirstInsert = true;
            while (size - numberOfInsertedElements < numberOfElements) {
                decreaseNumberOfInsertedElementsCondition.await();
            }
            numberOfInsertedElements += numberOfElements;
            for (int i = 0; i < numberOfElements; i++) {
                elements[insertIndex] = prefix + Integer.toString(i);
                insertIndex = next(insertIndex);
            }
//            System.out.println("Buffer> Inserted " + numberOfElements + " messages");
            this.addProducerTime(System.nanoTime() - start, numberOfElements);
            isFirstInsert = false;
            firstInsert.signal();
            increaseNumberOfInsertedElementsCondition.signal();
        } catch (Exception e) {
            System.out.println("Insert exception");
        } finally {
            lock.unlock();
        }
    }

    public ArrayList<String> get(int numberOfElements) {
        ArrayList<String> arrayList = new ArrayList<>();
        long start = System.nanoTime();
        lock.lock();
        try {
            if (isFirstRead) {
                this.firstRead.await();
            }
            isFirstRead = true;
            while (numberOfInsertedElements < numberOfElements) {
                increaseNumberOfInsertedElementsCondition.await();
            }
            for (int i = 0; i < numberOfElements; i++) {
                arrayList.add(elements[readingIndex]);
                readingIndex = next(readingIndex);
            }
            numberOfInsertedElements -= numberOfElements;
            this.addClientTime( System.nanoTime() - start, numberOfElements);
            isFirstRead = false;
            firstRead.signal();
            decreaseNumberOfInsertedElementsCondition.signal();
        } catch (Exception e) {
            System.out.println("Reading exception");
        } finally {
            lock.unlock();
        }
        return arrayList;
    }
}
