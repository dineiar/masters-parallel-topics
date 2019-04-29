package masters.experiment.base;

import java.util.ArrayList;

import masters.experiment.base.IntegerListThread.Operation;
import masters.listset.base.BaseMonitoredList;

public abstract class BaseIntegerExperiment<T extends BaseMonitoredList<Integer>> {
    public final int MONITORING_INTERVAL = 2; //seconds
    public static final int MIN_VALUE = 0; //inclusive
    public static final int MAX_VALUE = 100; //inclusive
    protected final int EXPERIMENT_RUNTIME = 120; //seconds

    protected ArrayList<IntegerListThread<T>> allThreads = new ArrayList<>();

    public abstract T getList();

    public void startThreads(int numThreads, Operation operation, final T list) {
        for (int i = 0; i < numThreads; i++) {
            IntegerListThread<T> t = new IntegerListThread<>(list, operation, null);
            allThreads.add(t);
            t.start();
        }
    }

    public void stopAllThreads() {
        for (IntegerListThread<T> t : allThreads) {
            t.terminate();
            try {
                t.join();
            } catch (InterruptedException ex) { System.err.println(ex.toString()); }
        }
    }

    public void runExperiment(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java <Experiment> numAddThreads numRemoveThreads numContainsThreads");
        } else {
            this.runExperiment(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }
    }
    public void runExperiment(int numAddThreads, int numRemoveThreads, int numContainsThreads) {
        this.getList().getMonitor().setNumAddThreads(numAddThreads);
        this.getList().getMonitor().setNumRemoveThreads(numRemoveThreads);
        this.getList().getMonitor().setNumContainsThreads(numContainsThreads);
        
        this.getList().startMonitor();

        startThreads(numAddThreads, Operation.Add, this.getList());
        startThreads(numRemoveThreads, Operation.Remove, this.getList());
        startThreads(numContainsThreads, Operation.Contains, this.getList());

        try { Thread.sleep(EXPERIMENT_RUNTIME * 1000); } catch (InterruptedException ex) { System.err.println(ex.toString()); }

        stopAllThreads();
        this.getList().stopMonitor();
    }
}