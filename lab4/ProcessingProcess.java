package lab4;

import lab2.BinarySemaphor;

import lab2.Semaphore;

import java.util.concurrent.Callable;

/**
 * Created on 11.11.19
 */
public class ProcessingProcess extends Process {
    private Process next;
    private ProcessMethod method;
    private int sleepTime;

    public interface ProcessMethod{
        String process(String data);
    }


    public ProcessingProcess(Buffer buffer, Process next, ProcessMethod method, int sleepTime) {
        super(buffer);
        this.next = next;
        this.method = method;
        this.sleepTime = sleepTime;
    }

    private void process(){
        this.buffer.startProcessing(this.index);
        this.buffer.edit(this.index, method.process(this.buffer.get(this.index)));
        this.buffer.endProcessing(this.index);
        this.index=this.buffer.next(this.index);
        try{
            Thread.sleep(this.sleepTime);
        }catch (Exception e){
            System.out.println("Sleeep exception");
        }
    }

    @Override
    public void run() {
        while(true){
            this.initSemaphore.P();
            this.process();
            this.next.init();
        }
    }
}
