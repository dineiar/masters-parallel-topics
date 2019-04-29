package masters.experiment;

import masters.experiment.base.BaseIntegerExperiment;
import masters.experiment.base.Monitor;
import masters.listset.CoarseList;

public class CoarseListExperiment extends BaseIntegerExperiment<CoarseList<Integer>> {

    public CoarseList<Integer> coarseList;

    public CoarseListExperiment() {
        Monitor<Integer> monitor = new Monitor<>("CoarseList", MONITORING_INTERVAL);
        this.coarseList = new CoarseList<>(monitor, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        new CoarseListExperiment().runExperiment(args);
    }

    @Override
    public CoarseList<Integer> getList() {
        return this.coarseList;
    }
}
