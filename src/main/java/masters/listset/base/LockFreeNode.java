package masters.listset.base;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class LockFreeNode<T> {
    public LockFreeNode(T item) {
        this.item = item;
        this.key = item.hashCode();
    }

    public T item;
    public int key;
    public AtomicMarkableReference<LockFreeNode<T>> next = new AtomicMarkableReference<>(null, false);
}