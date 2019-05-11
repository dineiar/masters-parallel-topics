package masters.experiment;

import masters.experiment.base.BaseIntegerRandomExperiment;
import masters.experiment.base.Monitor;
import masters.listset.LazyList;

public class RandomLazyListExperiment extends BaseIntegerRandomExperiment<LazyList<Integer>> {

    public LazyList<Integer> lazyList;

    public RandomLazyListExperiment() {
        Monitor<Integer> monitor = new Monitor<>("RandomLazyList", MONITORING_INTERVAL);
        this.lazyList = new LazyList<>(monitor, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        new RandomLazyListExperiment().runExperiment(args);
    }

    @Override
    public LazyList<Integer> getList() {
        return this.lazyList;
    }
}
