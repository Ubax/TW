package lab9.Serial;

import lab9.IResource;

import java.util.concurrent.Semaphore;

public class ResourceSerial implements IResource {
    private Semaphore accessSemaphore = new Semaphore(1);

    public void getReadingAccess() throws InterruptedException{
        accessSemaphore.acquire();
    }

    public void getWritingAccess() throws InterruptedException{
        accessSemaphore.acquire();
    }

    public void releaseReadingAccess() throws InterruptedException{
        accessSemaphore.release();
    }

    public void releaseWritingAccess(){
        accessSemaphore.release();
    }
}
