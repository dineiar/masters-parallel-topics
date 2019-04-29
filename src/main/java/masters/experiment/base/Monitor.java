package masters.experiment.base;

import java.util.concurrent.atomic.AtomicInteger;

import masters.listset.base.BaseMonitoredList;

public class Monitor<T> extends Thread {
    private int monitoringInterval; //seconds

    private String experimentName;
    private BaseMonitoredList<T> list;

    private int numAddThreads;
    private int numRemoveThreads;
    private int numContainsThreads;
    private int totalThreads;

    private AtomicInteger numAddOperations = new AtomicInteger(0);
    private AtomicInteger numRemoveOperations = new AtomicInteger(0);
    private AtomicInteger numContainsOperations = new AtomicInteger(0);

    private volatile boolean running = true;
    
    public Monitor(String name, int monitoringInterval) {
        this.experimentName = name;
        this.monitoringInterval = monitoringInterval;
    }

    public void setList(BaseMonitoredList<T> list) {
        this.list = list;
    }
    
    public void setNumAddThreads(int numAddThreads) {
        this.numAddThreads = numAddThreads;
        this.totalThreads = numAddThreads + numRemoveThreads + numContainsThreads;
    };
    public void setNumRemoveThreads(int numRemoveThreads) {
        this.numRemoveThreads = numRemoveThreads;
        this.totalThreads = numAddThreads + numRemoveThreads + numContainsThreads;
    };
    public void setNumContainsThreads(int numContainsThreads) {
        this.numContainsThreads = numContainsThreads;
        this.totalThreads = numAddThreads + numRemoveThreads + numContainsThreads;
    };

    @Override
    public void run() {
        System.out.println("Experiment;Threads;Add;Remove;Contains;Population");
        while (this.running) {
            if (this.list != null) {
                String txt = experimentName + ";" + totalThreads + ";" + numAddOperations + ";" 
                    + numRemoveOperations + ";" + numContainsOperations + ";" + list.count();
                numAddOperations.set(0);
                numRemoveOperations.set(0);
                numContainsOperations.set(0);
                System.out.println(txt);
            }
            try {
                Thread.sleep(monitoringInterval * 1000);
            } catch (InterruptedException ex) {
                System.out.println("Interrupted Exception:");
                System.out.println(ex.toString());
            }
        }
    }

    public void terminate() {
        this.running = false;
    }

    public int countAddOperation() {
        return this.numAddOperations.incrementAndGet();
    }
    public int countRemoveOperation() {
        return this.numRemoveOperations.incrementAndGet();
    }
    public int countContainsOperation() {
        return this.numContainsOperations.incrementAndGet();
    }
}
