package lab9.WriterPriority;

import lab9.Runner;

public class Main {

    public static void main(String[] args) {
        ResourceWritersPriority resource = new ResourceWritersPriority();
        Runner.run(resource);
    }
}