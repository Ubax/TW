package lab9.ReadersPriroty;

import lab9.IResource;

import java.util.concurrent.Semaphore;

public class ResourceReadersPriority implements IResource {
    private Semaphore readingSemaphore = new Semaphore(1, false);
    private Semaphore writingSemaphore = new Semaphore(1, false);
    private Semaphore syncSemaphore = new Semaphore(1, false);

    private int currentlyWaiting = 0;

    public void getReadingAccess() throws InterruptedException{
        readingSemaphore.acquire();
        currentlyWaiting++;
        if(currentlyWaiting==1){
            writingSemaphore.acquire();
        }
        readingSemaphore.release();
    }

    public void getWritingAccess() throws InterruptedException{
        syncSemaphore.acquire();
        writingSemaphore.acquire();
    }

    public void releaseReadingAccess() throws InterruptedException{
        readingSemaphore.acquire();
        currentlyWaiting--;
        if(currentlyWaiting==0){
            writingSemaphore.release();
        }
        readingSemaphore.release();
    }

    public void releaseWritingAccess(){
        writingSemaphore.release();
        syncSemaphore.release();
    }
}
