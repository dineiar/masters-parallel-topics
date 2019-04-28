package masters.listset;

import java.util.concurrent.locks.ReentrantLock;

import masters.listset.def.Node;
import masters.listset.def.Set;

public class CoarseList<T> implements Set<T> {
    private Node<T> head;
    private final ReentrantLock lock = new ReentrantLock();

    public CoarseList(T headItem, T tailItem) {
        head = new Node<>(headItem);
        Node<T> tail = new Node<>(tailItem);
        head.next = tail;
    }

    @Override
    public boolean add(T item) {
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

}