package lab8;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class zad3 {
    public static void getData() throws Exception {

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new DataGetter().getData());
        }

        for (var s : data) {
            System.out.println(s);
        }

    }

    public static void getDataAsync() throws Exception {
        List<CompletableFuture<String>> futureData = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            futureData.add(new DataGetterAsync().getData());
        }
        for (var s : futureData) {
            System.out.println(s.get());
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("---- SYNC ----");
        long startSync = System.nanoTime();
        getData();
        long endSync = System.nanoTime();
        System.out.println("---- ASYNC ----");
        long startAsync = System.nanoTime();
        getDataAsync();
        long endAsync = System.nanoTime();

        System.out.println("Sync Time: " + ((double)endSync - startSync) / 1_000_000_000);
        System.out.println("Async Time: " + ((double) endAsync - startAsync) / 1_000_000_000);
    }
}
