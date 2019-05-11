package masters.experiment;

import masters.experiment.base.BaseIntegerRandomExperiment;
import masters.experiment.base.Monitor;
import masters.listset.CoarseList;

public class RandomCoarseListExperiment extends BaseIntegerRandomExperiment<CoarseList<Integer>> {

    public CoarseList<Integer> coarseList;

    public RandomCoarseListExperiment() {
        Monitor<Integer> monitor = new Monitor<>("RandomCoarseList", MONITORING_INTERVAL);
        this.coarseList = new CoarseList<>(monitor, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        new RandomCoarseListExperiment().runExperiment(args);
    }

    @Override
    public CoarseList<Integer> getList() {
        return this.coarseList;
    }
}
