package lab4.randomMessages.fair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 13.11.19
 */
public class main {
    public static void main(String[] args) {
        try {
            int M = 100000;
            int N = 100;
            Buffer buffer = new Buffer(2 * M, N);
            List<Thread> threads = new ArrayList<>();
            threads.add(new Thread(new BufferMonitor(buffer)));
            for (int i = 0; i < N; i++) threads.add(new Thread(new Producer(M, buffer)));
            for (int i = 0; i < N; i++) threads.add(new Thread(new Client(M, buffer)));
            for (var t : threads) {
                t.start();
            }
            for (var t : threads) {
                t.join();
            }
            Double avgTime = buffer.getAvgTime();
            System.out.println("Avg time: "+avgTime.toString());
        }catch (Exception e){
            System.out.println("Program exception");
        }
    }
}
