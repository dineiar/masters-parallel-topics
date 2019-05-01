package masters.listset.base;

public class NodeWithLockMark<T> extends NodeWithLock<T> {
    public NodeWithLockMark(T item) {
        super(item);
    }

    public NodeWithLockMark<T> next;
    
    public boolean mark = false;
}