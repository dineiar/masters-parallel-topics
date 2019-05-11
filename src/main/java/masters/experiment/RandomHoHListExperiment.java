package masters.experiment;

import masters.experiment.base.BaseIntegerRandomExperiment;
import masters.experiment.base.Monitor;
import masters.listset.HoHList;

public class RandomHoHListExperiment extends BaseIntegerRandomExperiment<HoHList<Integer>> {

    public HoHList<Integer> hohList;

    public RandomHoHListExperiment() {
        Monitor<Integer> monitor = new Monitor<>("RandomHoHList", MONITORING_INTERVAL);
        this.hohList = new HoHList<>(monitor, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        new RandomHoHListExperiment().runExperiment(args);
    }

    @Override
    public HoHList<Integer> getList() {
        return this.hohList;
    }
}
