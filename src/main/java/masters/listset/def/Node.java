package masters.listset.def;

public class Node<T> {
    public Node(T item) {
        this.item = item;
        this.key = item.hashCode();
    }

    public T item;
    public int key;
    public Node<T> next;
}