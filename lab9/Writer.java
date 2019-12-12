package lab9;

public class Writer implements Runnable {
    private IResource resource;

    private long sumOfTimes = 0;
    private long iterations = 0;

    public Writer(IResource resource) {
        this.resource = resource;
    }

    public double getAverageWaitingTime() {
        return ((double) sumOfTimes) / iterations;
    }

    @Override
    public void run() {
        long start = 0;
        for (int i = 0; i < 100; i++) {
            try {
                start = System.nanoTime();
                this.resource.getWritingAccess();
                this.sumOfTimes += System.nanoTime() - start;
                this.iterations++;
//                System.out.println("Writing");
                Thread.sleep(10);
                this.resource.releaseWritingAccess();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Writer finished");
    }
}
