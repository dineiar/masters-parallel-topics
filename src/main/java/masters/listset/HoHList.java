package masters.listset;

import masters.listset.base.NodeHoH;
import masters.experiment.base.Monitor;
import masters.listset.base.BaseMonitoredList;

/**
 * Hand-on-Hand locking list
 * @param <T> list items type
 */
public class HoHList<T> extends BaseMonitoredList<T> {
    private NodeHoH<T> head;
    private T headItem;
    private T tailItem;

    public HoHList(Monitor<T> monitor, T headItem, T tailItem) {
        super(monitor);
        this.headItem = headItem;
        this.tailItem = tailItem;
        head = new NodeHoH<>(headItem);
        NodeHoH<T> tail = new NodeHoH<>(tailItem);
        head.next = tail;
    }

    @Override
    public boolean add(T item) {
        super.add(item);
        NodeHoH<T> pred, curr;
        int key = item.hashCode();
        pred = head;
        pred.lock();
        try {
            curr = pred.next;
            curr.lock();
            try {
                while (curr.key < key) {
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
                if (key == curr.key) {
                    return false;
                } else {
                    NodeHoH<T> node = new NodeHoH<T>(item);
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
        NodeHoH<T> pred, curr;
        int key = item.hashCode();
        pred = head;
        pred.lock();
        try {
            curr = pred.next;
            curr.lock();
            try {
                while (curr.key < key) {
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
                if (key == curr.key) {
                    return true;
                } else {
                    return false;
                }
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    @Override
    public boolean remove(T item) {
        super.remove(item);
        NodeHoH<T> pred, curr;
        int key = item.hashCode();
        pred = head;
        pred.lock();
        try {
            curr = pred.next;
            curr.lock();
            try {
                while (curr.key < key) {
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
                if (key == curr.key) {
                    pred.next = curr.next;
                    return true;
                } else {
                    return false;
                }
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    @Override
    public int count() {
        int count = 0;
        NodeHoH<T> pred, curr;
        pred = head;
        pred.lock();
        try {
            curr = pred.next;
            curr.lock();
            try {
                while (curr.next != null) {
                    count++;
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
        return count;
    }

    @Override
    public String printToString() {
        int count = 0;
        NodeHoH<T> pred, curr;
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

}