package masters.experiment.base;

import java.util.concurrent.ThreadLocalRandom;

import masters.listset.base.IList;

public class IntegerListThread<T extends IList<Integer>> extends Thread {
    public enum Operation {
        Add, Remove, Contains
    }

    protected Operation operation;
    protected T list;
    protected Integer waitInterval; //seconds

    protected volatile boolean running = true;

    public IntegerListThread(T list, Operation operation, Integer waitInterval) {
        this.list = list;
        this.operation = operation;
        this.waitInterval = waitInterval;
    }

    public int getRandom() {
        return ThreadLocalRandom.current().nextInt(BaseIntegerExperiment.MIN_VALUE, BaseIntegerExperiment.MAX_VALUE + 1);
    }

    @Override
    public void run() {
        while (this.running) {
            this.doOperation();
        }
    }
    
    protected void doOperation() {
        switch (operation) {
            case Add:
                this.list.add(getRandom());
                break;
            case Remove:
                this.list.remove(getRandom());
                break;
            case Contains:
                this.list.contains(getRandom());
                break;
        }
        if (this.waitInterval != null && this.waitInterval > 0) {
            try {
                Thread.sleep(waitInterval * 1000);
            } catch (InterruptedException ex) {
                System.out.println("Interrupted Exception:");
                System.out.println(ex.toString());
            }
        }

    }

    public void terminate() {
        this.running = false;
    }
}