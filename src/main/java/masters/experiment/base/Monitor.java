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

    private long monitoringCount;
    private long meanPopulation;
    private long meanAddOperations;
    private long meanRemoveOperations;
    private long meanContainsOperations;

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
                int population = list.count();
                int addOperations = numAddOperations.get();
                int removeOperations = numRemoveOperations.get();
                int containsOperations = numContainsOperations.get();
                String txt = experimentName + ";" + totalThreads + ";" + addOperations + ";" 
                    + removeOperations + ";" + containsOperations + ";" + population;
                numAddOperations.set(0);
                numRemoveOperations.set(0);
                numContainsOperations.set(0);
                monitoringCount++;
                meanPopulation += population;
                meanAddOperations += addOperations;
                meanRemoveOperations += removeOperations;
                meanContainsOperations += containsOperations;
                
                System.out.println(txt);
            }

            try {
                Thread.sleep(monitoringInterval * 1000);
            } catch (InterruptedException ex) {
                System.out.println("Interrupted Exception:");
                System.out.println(ex.toString());
            }
        }
        meanPopulation /= monitoringCount;
        meanAddOperations /= monitoringCount;
        meanRemoveOperations /= monitoringCount;
        meanContainsOperations /= monitoringCount;
        String txt = experimentName + " (Mean);" + totalThreads + ";" + meanAddOperations + ";" 
            + meanRemoveOperations + ";" + meanContainsOperations + ";" + meanPopulation;
        System.out.println(txt);
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
