package lab4.randomMessages.fair;

/**
 * Created on 14.11.19
 */
public class BufferMonitor implements Runnable{
    private final Buffer buffer;

    public BufferMonitor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        boolean eventHappened;
        try {
            while (!buffer.shouldFinish()) {
                eventHappened = false;
                InsertOrder insertOrder=buffer.getInsertOrder();
                ReadOrder readOrder=buffer.getReadOrder();
                if (insertOrder != null && buffer.getNumberOfFreeElements()>= insertOrder.getNumberOfElements()) {
                    buffer.insert();
                    eventHappened = true;
                }
                if (readOrder != null && buffer.getNumberOfInsertedElements()>= readOrder.getNumberOfElements()) {
                    buffer.read();
                    eventHappened = true;
                }

                if(!eventHappened){
                    buffer.getEventSemaphore().P();
                }
            }
            System.out.println("Buffer monitor> Finished");
        }catch (Exception e){
            System.out.println("Buffer exception");
            e.printStackTrace();
        }
    }
}
