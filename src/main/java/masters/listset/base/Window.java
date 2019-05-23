package masters.listset.base;

public class Window<T> {
    public LockFreeNode<T> pred;
    public LockFreeNode<T> curr;

    public Window(LockFreeNode<T> pred, LockFreeNode<T> curr) {
        this.pred = pred;
        this.curr = curr;
    }
}