package masters.experiment;

import masters.experiment.base.BaseIntegerExperiment;
import masters.experiment.base.Monitor;
import masters.listset.HoHContainsLockFreeList;

public class HoHContainsLockFreeListExperiment extends BaseIntegerExperiment<HoHContainsLockFreeList<Integer>> {

    public HoHContainsLockFreeList<Integer> hohList;

    public HoHContainsLockFreeListExperiment() {
        Monitor<Integer> monitor = new Monitor<>("HoHContainsLockFreeList", MONITORING_INTERVAL);
        this.hohList = new HoHContainsLockFreeList<>(monitor, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        new HoHContainsLockFreeListExperiment().runExperiment(args);
    }

    @Override
    public HoHContainsLockFreeList<Integer> getList() {
        return this.hohList;
    }
}
