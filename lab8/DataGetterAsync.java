package lab8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class DataGetterAsync {
    public CompletableFuture<String> getData() {
        CompletableFuture<String> async = new CompletableFuture<>();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                URL oracle = new URL("http://localhost:3000/");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(oracle.openStream()));

                String inputLine;
                StringBuilder data = new StringBuilder();
                while ((inputLine = in.readLine()) != null)
                    data.append(inputLine);
                in.close();
                //oznaczamy `CompletableFuture` jako skończony
                async.complete(data.toString());
            } catch (Exception e) {
                async.completeExceptionally(e);
            }
        });

        return async;
    }
}
