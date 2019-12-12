package lab9;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void run(IResource resource){
        List<Writer> writers = new ArrayList<>();
        List<Reader> readers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            writers.add(new Writer(resource));
        }
        for (int i = 0; i < 6; i++) {
            readers.add(new Reader(resource));
        }
        for (var w : writers) threads.add(new Thread(w));
        for (var r : readers) threads.add(new Thread(r));
        for (var t : threads) t.start();
        for (var t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                break;
            }
        }
        double avgWriters = 0.0;
        for (var w : writers) {
            avgWriters += w.getAverageWaitingTime();
        }
        avgWriters /= writers.size();
        double avgReaders = 0.0;
        for (var r : readers) {
            avgReaders += r.getAverageWaitingTime();
        }
        avgReaders /= readers.size();

        System.out.println("Avg writers: " + avgWriters/1000_000+" ms");
        System.out.println("Avg readers: " + avgReaders/1000_000+" ms");
    }
}
