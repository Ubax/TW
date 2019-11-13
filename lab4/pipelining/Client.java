package lab4.pipelining;

/**
 * Created on 11.11.19
 */
public class Client extends Process {
    public Client(Buffer buffer) {
        super(buffer);
    }

    @Override
    public void run() {
        while(true){
            this.initSemaphore.P();
            System.out.println("Client init");
            this.buffer.startProcessing(this.index);
            System.out.println("Got: "+this.buffer.get(this.index));
            this.buffer.clear(this.index);
            System.out.println("Cleaned");
            this.buffer.endProcessing(this.index);
            this.index=this.buffer.next(this.index);
        }
    }
}
