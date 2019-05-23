package masters.listset;

import java.util.concurrent.atomic.AtomicMarkableReference;

import masters.experiment.base.Monitor;
import masters.listset.base.BaseMonitoredList;
import masters.listset.base.LockFreeNode;
import masters.listset.base.Window;

/**
 * 
 * @param <T> list items type
 */
public class LockFreeList<T> extends BaseMonitoredList<T> {
    private LockFreeNode<T> head;
    private T headItem;
    private T tailItem;

    public LockFreeList(Monitor<T> monitor, T headItem, T tailItem) {
        super(monitor);
        this.headItem = headItem;
        this.tailItem = tailItem;
        head = new LockFreeNode<>(headItem);
        LockFreeNode<T> tail = new LockFreeNode<>(tailItem);
        head.next = new AtomicMarkableReference<>(tail, false);
    }

    public Window<T> find(LockFreeNode<T> head, int key) {
        LockFreeNode<T> pred = null;
        LockFreeNode<T> curr = null;
        LockFreeNode<T> succ = null;
        boolean[] marked = {false};
        boolean snip;
        retry: while (true) {
            pred = head;
            curr = pred.next.getReference();
            while (true) {
                if (curr.next != null) {
                    succ = curr.next.get(marked);
                    while (marked[0]) {
                        snip = pred.next.compareAndSet(curr, succ, false, false);
                        if (!snip) continue retry;
                        curr = succ;
                        succ = curr.next.get(marked);
                    }
                }
                if (curr.key >= key) {
                    return new Window<T>(pred, curr);
                }
                pred = curr;
                curr = succ;
            }
        }
    }

    @Override
    public boolean add(T item) {
        super.add(item);
        int key = item.hashCode();
        while (true) {
            Window<T> window = find(head, key);
            if (window.curr.key == key) {
                return false;
            } else {
                LockFreeNode<T> node = new LockFreeNode<T>(item);
                node.next = new AtomicMarkableReference<>(window.curr, false);
                if (window.pred.next.compareAndSet(window.curr, node, false, false)) {
                    return true;
                }
            }
        }
    }

    @Override
    public boolean contains(T item) {
        super.contains(item);
        int key = item.hashCode();
        boolean[] marked = {false};
        LockFreeNode<T> curr = head;
        while (curr.key < key) {
            curr = curr.next.getReference();
            curr.next.get(marked);
        }
        return curr.key == key && !marked[0];
    }

    @Override
    public boolean remove(T item) {
        super.remove(item);
        int key = item.hashCode();
        boolean snip;
        while (true) {
            Window<T> window = find(head, key);
            if (window.curr.key != key) {
                return false;
            } else {
                LockFreeNode<T> succ = window.curr.next.getReference();
                snip = window.curr.next.attemptMark(succ, true);
                if (!snip) continue;
                window.pred.next.compareAndSet(window.curr, succ, false, false);
                return true;
            }
        }
    }

    @Override
    public int count() {
        int count = 0;
        boolean[] marked = {false};
        LockFreeNode<T> curr = head;
        while (curr.next.getReference() != null) {
            curr = curr.next.getReference();
            curr.next.get(marked);
            if (!marked[0]) count++;
        }
        return count-1;
    }

    @Override
    public String printToString() {
        int count = 0;
        boolean[] marked = {false};
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        LockFreeNode<T> curr = head;
        while (curr.next.getReference() != null) {
            curr = curr.next.getReference();
            curr.next.get(marked);
            if (!marked[0] && curr.item != this.tailItem) {
                count++;
                sb.append(curr.key + ", ");
            }
        }
        sb.append("]");
        return count + " items: " + sb.toString();
    }

}