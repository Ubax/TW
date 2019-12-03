package lab8;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class zad2 {
    public static void main(String[] args) {
        Random random = new Random();
        int[] arr = IntStream.range(0, 30_000_000).map(x -> random.nextInt()).toArray();
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Summer summerBig = new Summer(arr, 0, arr.length - 1, 100_000_000);
        Summer summerAvg = new Summer(arr, 0, arr.length - 1, 1_000_000);
        Summer summerSml = new Summer(arr, 0, arr.length - 1, 1_000);
        Summer summerMicro = new Summer(arr, 0, arr.length - 1, 1_00);
        long start = System.nanoTime();
        int sum = forkJoinPool.invoke(summerBig);
        long end = System.nanoTime();
        System.out.println("[BIG] Sum: " + sum);
        System.out.println("[BIG] Time: " + ((double)(end - start))/1_000_000 + " ms");

        start = System.nanoTime();
        sum = forkJoinPool.invoke(summerAvg);
        end = System.nanoTime();
        System.out.println("[AVG] Sum: " + sum);
        System.out.println("[AVG] Time: " + ((double)(end - start))/1_000_000 + " ms");

        start = System.nanoTime();
        sum = forkJoinPool.invoke(summerSml);
        end = System.nanoTime();
        System.out.println("[SML] Sum: " + sum);
        System.out.println("[SML] Time: " + ((double)(end - start))/1_000_000 + " ms");

        start = System.nanoTime();
        sum = forkJoinPool.invoke(summerMicro);
        end = System.nanoTime();
        System.out.println("[MIC] Sum: " + sum);
        System.out.println("[MIC] Time: " + ((double)(end - start))/1_000_000 + " ms");

//        start = System.nanoTime();
//        sum = forkJoinPool.invoke(summerAvg);
//        end = System.nanoTime();
//        System.out.println("[AVG] Sum: " + sum);
//        System.out.println("[AVG] Time: " + ((double)(end - start))/1_000_000 + " ms");
    }
}
