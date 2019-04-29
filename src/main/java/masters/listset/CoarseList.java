package masters.listset;

import java.util.concurrent.locks.ReentrantLock;

import masters.listset.base.Node;
import masters.experiment.base.Monitor;
import masters.listset.base.BaseMonitoredList;

public class CoarseList<T> extends BaseMonitoredList<T> {
    private Node<T> head;
    private final ReentrantLock lock = new ReentrantLock();
    private T headItem;
    private T tailItem;

    public CoarseList(Monitor<T> monitor, T headItem, T tailItem) {
        super(monitor);
        this.headItem = headItem;
        this.tailItem = tailItem;
        head = new Node<>(headItem);
        Node<T> tail = new Node<>(tailItem);
        head.next = tail;
    }

    @Override
    public boolean add(T item) {
        super.add(item);
        Node<T> pred, curr;
        int key = item.hashCode();
        lock.lock();
        try {
            pred = head;
            curr = pred.next;
            while (pred.next != null && curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            if (key == curr.key) {
                return false;
            } else {
                Node<T> node = new Node<T>(item);
                node.next = curr;
                pred.next = node;
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean contains(T item) {
        super.contains(item);
        Node<T> pred, curr;
        int key = item.hashCode();
        lock.lock();
        try {
            pred = head;
            curr = pred.next;
            while (pred.next != null && curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            if (key == curr.key) {
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean remove(T item) {
        super.remove(item);
        Node<T> pred, curr;
        int key = item.hashCode();
        lock.lock();
        try {
            pred = head;
            curr = pred.next;
            while (pred.next != null && curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            if (key == curr.key) {
                pred.next = curr.next;
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int count() {
        int count = 0;
        Node<T> pred, curr;
        lock.lock();
        try {
            pred = head;
            curr = pred.next;
            while (pred.next != null) {
                count++;
                pred = curr;
                curr = curr.next;
            }
        } finally {
            lock.unlock();
        }
        return count-1; //discounts head item
    }

    @Override
    public String printToString() {
        int count = 0;
        Node<T> pred, curr;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        lock.lock();
        try {
            pred = head;
            curr = pred.next;
            while (pred.next != null) {
                if (curr.item != this.tailItem) {
                    count++;
                    sb.append(curr.key + ", ");
                }
                pred = curr;
                curr = curr.next;
            }
            sb.append("]");
        } finally {
            lock.unlock();
        }
        return count + " items: " + sb.toString();
    }

}