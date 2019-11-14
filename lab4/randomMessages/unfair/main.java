package lab4.randomMessages.unfair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 13.11.19
 */
public class main {
    public static void main(String[] args) {
        try {
            int M = 1_000;
            int N = 1_000;
            Buffer buffer = new Buffer(2 * M, N);
            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < N; i++) threads.add(new Thread(new Producer(M, buffer)));
            for (int i = 0; i < N; i++) threads.add(new Thread(new Client(M, buffer)));
            for (var t : threads) {
                t.start();
            }
            for (var t : threads) {
                t.join();
            }
            System.out.println("AVG "+buffer.getAvgTime());
        }catch (Exception e){
            System.out.println("Program exception");
        }
    }
}
