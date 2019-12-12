package lab8;

public class ReduceState {
    private boolean prevSpace;
    private long count;

    public ReduceState() {
        this.prevSpace = true;
        this.count = 0;
    }

    public boolean isPrevSpace() {
        return prevSpace;
    }

    public long getCount() {
        return count;
    }

    public void markSpace() {
        this.prevSpace = true;
    }

    public void unmarkSpace() {
        this.prevSpace = false;
    }

    public ReduceState merge(ReduceState reduceState) {
        this.count += reduceState.getCount();
        return this;
    }

    public void inc() {
        this.count++;
    }
}
