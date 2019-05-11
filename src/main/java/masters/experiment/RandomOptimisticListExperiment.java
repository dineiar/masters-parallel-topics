package masters.experiment;

import masters.experiment.base.BaseIntegerRandomExperiment;
import masters.experiment.base.Monitor;
import masters.listset.OptimisticList;

public class RandomOptimisticListExperiment extends BaseIntegerRandomExperiment<OptimisticList<Integer>> {

    public OptimisticList<Integer> lazyList;

    public RandomOptimisticListExperiment() {
        Monitor<Integer> monitor = new Monitor<>("RandomOptimisticList", MONITORING_INTERVAL);
        this.lazyList = new OptimisticList<>(monitor, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        new RandomOptimisticListExperiment().runExperiment(args);
    }

    @Override
    public OptimisticList<Integer> getList() {
        return this.lazyList;
    }
}
