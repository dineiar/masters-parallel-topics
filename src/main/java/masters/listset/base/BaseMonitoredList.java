package masters.listset.base;

import masters.experiment.base.Monitor;

public abstract class BaseMonitoredList<T> implements IList<T> {
    protected Monitor<T> monitor;

    public BaseMonitoredList(Monitor<T> monitor) {
        monitor.setList(this);
        this.monitor = monitor;
    }

    public Monitor<T> getMonitor() {
        return this.monitor;
    }
    public void startMonitor() {
        this.monitor.start();
    }
    public void stopMonitor() {
        this.monitor.terminate();
        try {
            this.monitor.join();
        } catch (InterruptedException ex) { System.err.println(ex.toString()); }
    }

    public boolean add(T x) {
        this.monitor.countAddOperation();
        return false;
    }
    public boolean remove(T x) {
        this.monitor.countRemoveOperation();
        return false;
    }
    public boolean contains(T x) {
        this.monitor.countContainsOperation();
        return false;
    }

    public abstract int count();
    public abstract String printToString();

    @Override
    public String toString() {
        return this.printToString();
    }
}