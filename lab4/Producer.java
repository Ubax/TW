package lab4;

/**
 * Created on 11.11.19
 */
public class Producer extends Process {
    private Process next;
    private int shortSleep;
    private int longSleep;

    public Producer(Buffer buffer, Process next, int shortSleep, int longSleep) {
        super(buffer);
        this.next = next;
        this.shortSleep = shortSleep;
        this.longSleep = longSleep;
    }

    private int doIteration(Integer iteration){
        char z = (char)(iteration+'A');
        String data = String.valueOf(z);
        this.buffer.insert(data);
        System.out.println("Inserted: "+data);
        return iteration+1;
    }

    @Override
    public void run() {
        int i=0;
        this.initSemaphore.P();
        while (true){
            i=doIteration(i);
            this.next.init();
            try {
                if (i < 5) Thread.sleep(shortSleep);
                else {
                    i = 0;
                    Thread.sleep(longSleep);
                }
            }catch (Exception e){
                System.out.println("Producer exception");
            }
        }
    }

}
