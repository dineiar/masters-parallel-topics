package masters.listset.base;

public interface IList<T> {
    public boolean add(T x);
    public boolean remove(T x);
    public boolean contains(T x);
    public int count();
    public String printToString();
}
