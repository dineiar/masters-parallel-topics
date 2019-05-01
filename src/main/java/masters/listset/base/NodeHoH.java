package masters.listset.base;

import java.util.concurrent.locks.ReentrantLock;

public class NodeHoH<T> {
    public NodeHoH(T item) {
        this.item = item;
        this.key = item.hashCode();
    }

    private final ReentrantLock lock = new ReentrantLock();
    public T item;
    public int key;
    public NodeHoH<T> next;

    public void lock() {
        lock.lock();
    }
    public void unlock() {
        lock.unlock();
    }
}