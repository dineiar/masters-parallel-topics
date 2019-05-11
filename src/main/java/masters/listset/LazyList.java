package masters.listset;

import masters.listset.base.NodeWithLockMark;
import masters.experiment.base.Monitor;
import masters.listset.base.BaseMonitoredList;

/**
 * Hand-on-Hand locking lazy list
 * @param <T> list items type
 */
public class LazyList<T> extends BaseMonitoredList<T> {
    private NodeWithLockMark<T> head;
    private T headItem;
    private T tailItem;

    public LazyList(Monitor<T> monitor, T headItem, T tailItem) {
        super(monitor);
        this.headItem = headItem;
        this.tailItem = tailItem;
        head = new NodeWithLockMark<>(headItem);
        NodeWithLockMark<T> tail = new NodeWithLockMark<>(tailItem);
        head.next = tail;
    }

    @Override
    public boolean add(T item) {
        super.add(item);
        NodeWithLockMark<T> pred, curr;
        int key = item.hashCode();
        pred = head;
        pred.lock();
        try {
            curr = pred.next;
            curr.lock();
            try {
                while (curr.key < key) {
                    // Delete marked nodes
                    if (curr.mark) {
                        pred.next = curr.next;
                        curr = curr.next;
                        curr.lock();
                    } else {
                        pred.unlock();
                        pred = curr;
                        curr = curr.next;
                        curr.lock();
                    }
                }
                if (key == curr.key) {
                    return false;
                } else {
                    NodeWithLockMark<T> node = new NodeWithLockMark<T>(item);
                    node.next = curr;
                    pred.next = node;
                    return true;
                }
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    @Override
    public boolean contains(T item) {
        super.contains(item);
        int key = item.hashCode();
        NodeWithLockMark<T> curr = head;
        while (curr.key < key) {
            curr = curr.next;
        }
        return curr.key == key && !curr.mark;
    }

    @Override
    public boolean remove(T item) {
        super.remove(item);
        int key = item.hashCode();
        NodeWithLockMark<T> curr = head;
        while (curr.key < key) {
            curr = curr.next;
        }
        if (key == curr.key) {
            curr.mark = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int count() {
        int count = 0;
        NodeWithLockMark<T> curr = head;
        while (curr.next != null) {
            if (!curr.mark) count++;
            curr = curr.next;
        }
        return count-1;
    }

    @Override
    public String printToString() {
        int count = 0;
        NodeWithLockMark<T> pred, curr;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        pred = head;
        pred.lock();
        try {
            curr = pred.next;
            curr.lock();
            try {
                while (curr.next != null) {
                    if (!curr.mark && curr.item != this.tailItem) {
                        count++;
                        sb.append(curr.key + ", ");
                    }
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
            } finally {
                curr.unlock();
            }
            sb.append("]");
        } finally {
            pred.unlock();
        }
        return count + " items: " + sb.toString();
    }

}