package masters.experiment.base;

import masters.listset.base.BaseMonitoredList;

public abstract class BaseIntegerRandomExperiment<T extends BaseMonitoredList<Integer>> extends BaseIntegerExperiment<T> {

    public void startThreads(int numThreads, final T list) {
        for (int i = 0; i < numThreads; i++) {
            IntegerListThread<T> t = new IntegerListRandomThread<>(list, null);
            allThreads.add(t);
            t.start();
        }
    }

    @Override
    public void runExperiment(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java <Experiment> numThreads");
        } else {
            this.runExperiment(Integer.parseInt(args[0]));
        }
    }
    public void runExperiment(int numThreads) {
        this.getList().getMonitor().setNumAddThreads(numThreads);
        this.getList().getMonitor().setNumRemoveThreads(0);
        this.getList().getMonitor().setNumContainsThreads(0);
        
        this.getList().startMonitor();

        startThreads(numThreads, this.getList());

        try { Thread.sleep(EXPERIMENT_RUNTIME * 1000); } catch (InterruptedException ex) { System.err.println(ex.toString()); }

        stopAllThreads();
        this.getList().stopMonitor();
    }
}