package lab9.ConcurrentReading;

import lab9.Runner;

public class Main {

    public static void main(String[] args) {
        ResourceConcurrentReading resource = new ResourceConcurrentReading();
        Runner.run(resource);
    }
}