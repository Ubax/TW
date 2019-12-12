package lab9;

public interface IResource {
    void getReadingAccess() throws InterruptedException;

    void getWritingAccess() throws InterruptedException;

    void releaseReadingAccess() throws InterruptedException;

    void releaseWritingAccess();
}
