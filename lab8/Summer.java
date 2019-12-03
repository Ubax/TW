package lab8;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class Summer extends RecursiveTask<Integer> {
    private int[] array;
    private int start;
    private int end;
    private final int MIN_DIVISION_LENGTH;

    public Summer(int[] array, int start, int end) {
        this(array, start, end, 10_000_000);
    }

    public Summer(int[] array, int start, int end, int level) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.MIN_DIVISION_LENGTH = level;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if (end - start < MIN_DIVISION_LENGTH) {
            sum = Arrays.stream(array, start, end + 1).reduce(0, Integer::sum);
        } else {
            Summer a = new Summer(array, start, (start + end) / 2);
            Summer b = new Summer(array, (start + end) / 2 + 1, end);

            a.fork();
            b.fork();

            sum = a.join() + b.join();
        }
        return sum;
    }
}
