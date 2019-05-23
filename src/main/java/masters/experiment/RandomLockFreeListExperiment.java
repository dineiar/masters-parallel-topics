package masters.experiment;

import masters.experiment.base.BaseIntegerRandomExperiment;
import masters.experiment.base.Monitor;
import masters.listset.LockFreeList;

public class RandomLockFreeListExperiment extends BaseIntegerRandomExperiment<LockFreeList<Integer>> {

    public LockFreeList<Integer> list;

    public RandomLockFreeListExperiment() {
        Monitor<Integer> monitor = new Monitor<>("RandomLockFreeList", MONITORING_INTERVAL);
        this.list = new LockFreeList<>(monitor, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        new RandomLockFreeListExperiment().runExperiment(args);
    }

    @Override
    public LockFreeList<Integer> getList() {
        return this.list;
    }
}
