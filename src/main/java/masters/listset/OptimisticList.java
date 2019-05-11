package masters.listset;

import masters.listset.base.NodeWithLock;
import masters.experiment.base.Monitor;
import masters.listset.base.BaseMonitoredList;

/**
 * Hand-on-Hand locking list
 * @param <T> list items type
 */
public class OptimisticList<T> extends BaseMonitoredList<T> {
    private NodeWithLock<T> head;
    private T headItem;
    private T tailItem;

    public OptimisticList(Monitor<T> monitor, T headItem, T tailItem) {
        super(monitor);
        this.headItem = headItem;
        this.tailItem = tailItem;
        head = new NodeWithLock<>(headItem);
        NodeWithLock<T> tail = new NodeWithLock<>(tailItem);
        head.next = tail;
    }

    @Override
    public boolean add(T item) {
        super.add(item);
        int key = item.hashCode();
        while (true) {
            NodeWithLock<T> pred = head;
            NodeWithLock<T> curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            curr.lock();
            try {
                if (validate(pred, curr)) {
                    if (key == curr.key) {
                        return false;
                    } else {
                        NodeWithLock<T> node = new NodeWithLock<T>(item);
                        node.next = curr;
                        pred.next = node;
                        return true;
                    }
                }
            } finally {
                pred.unlock();
                curr.unlock();
            }
        }
    }

    @Override
    public boolean contains(T item) {
        super.contains(item);
        int key = item.hashCode();
        while (true) {
            NodeWithLock<T> pred = head;
            NodeWithLock<T> curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            curr.lock();
            try {
                if (validate(pred, curr)) {
                    return key == curr.key;
                }
            } finally {
                pred.unlock();
                curr.unlock();
            }
        }
    }

    @Override
    public boolean remove(T item) {
        super.remove(item);
        int key = item.hashCode();
        while (true) {
            NodeWithLock<T> pred = head;
            NodeWithLock<T> curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            curr.lock();
            try {
                if (validate(pred, curr)) {
                    if (key == curr.key) {
                        pred.next = curr.next;
                        return true;
                    } else {
                        return false;
                    }
                }
            } finally {
                pred.unlock();
                curr.unlock();
            }
        }
    }

    @Override
    public int count() {
        int count = 0;
        NodeWithLock<T> node = head;
        while (node.next != null) {
            count++;
            node = node.next;
        }
        return count-1;
    }

    @Override
    public String printToString() {
        int count = 0;
        NodeWithLock<T> pred, curr;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        pred = head;
        pred.lock();
        try {
            curr = pred.next;
            curr.lock();
            try {
                while (curr.next != null) {
                    if (curr.item != this.tailItem) {
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

    private boolean validate(NodeWithLock<T> pred, NodeWithLock<T> curr) {
        NodeWithLock<T> node = head;
        while (node.key <= pred.key) {
            if (node == pred) {
                return pred.next == curr;
            }
            node = node.next;
        }
        return false;
    }

}