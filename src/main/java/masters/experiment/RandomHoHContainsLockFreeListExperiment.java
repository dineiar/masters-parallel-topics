package masters.experiment;

import masters.experiment.base.BaseIntegerRandomExperiment;
import masters.experiment.base.Monitor;
import masters.listset.HoHContainsLockFreeList;

public class RandomHoHContainsLockFreeListExperiment extends BaseIntegerRandomExperiment<HoHContainsLockFreeList<Integer>> {

    public HoHContainsLockFreeList<Integer> hohList;

    public RandomHoHContainsLockFreeListExperiment() {
        Monitor<Integer> monitor = new Monitor<>("RandomHoHContainsLockFreeList", MONITORING_INTERVAL);
        this.hohList = new HoHContainsLockFreeList<>(monitor, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        new RandomHoHContainsLockFreeListExperiment().runExperiment(args);
    }

    @Override
    public HoHContainsLockFreeList<Integer> getList() {
        return this.hohList;
    }
}
