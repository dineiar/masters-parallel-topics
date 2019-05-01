package masters.listset.base;

import java.util.concurrent.locks.ReentrantLock;

public class NodeWithLock<T> extends Node<T> {
    public NodeWithLock(T item) {
        super(item);
    }

    private final ReentrantLock lock = new ReentrantLock();
    public NodeWithLock<T> next;

    public void lock() {
        lock.lock();
    }
    public void unlock() {
        lock.unlock();
    }
}