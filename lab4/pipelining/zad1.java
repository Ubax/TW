package lab4.pipelining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created on 11.11.19
 */
public class zad1 {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(1);
        List<Process> processes = new ArrayList<>();
        processes.add(new Client(buffer));
        processes.add(new ProcessingProcess(buffer, processes.get(processes.size() - 1), x -> {
            System.out.println("Process 4: " + x);
            return x + "%" + x.toLowerCase();
        }, 100));
        processes.add(new ProcessingProcess(buffer, processes.get(processes.size() - 1), x -> {
            System.out.println("Process 3: " + x);
            return x + "$" + x.substring(0, 2);
        },150));
        processes.add(new ProcessingProcess(buffer, processes.get(processes.size() - 1), x -> {
            System.out.println("Process 2: " + x);
            return x + "@" + x;
        },200));
        processes.add(new ProcessingProcess(buffer, processes.get(processes.size() - 1), x -> {
            System.out.println("Process 1: " + x);
            return x.toUpperCase() + "#" + x;
        },300));
        processes.add(new Producer(buffer, processes.get(processes.size() - 1), 500,10000));
        Collections.reverse(processes);
        List<Thread> threads = new ArrayList<>();
        for (var p : processes) {
            threads.add(new Thread(p));
            threads.get(threads.size() - 1).start();
        }
        processes.get(0).init();
        for (var t : threads) {
            try {
                t.join();
            } catch (Exception e) {
                System.out.println("Join exception");
            }
        }
    }
}
