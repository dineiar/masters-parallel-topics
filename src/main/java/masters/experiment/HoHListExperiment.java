package masters.experiment;

import masters.experiment.base.BaseIntegerExperiment;
import masters.experiment.base.Monitor;
import masters.listset.HoHList;

public class HoHListExperiment extends BaseIntegerExperiment<HoHList<Integer>> {

    public HoHList<Integer> hohList;

    public HoHListExperiment() {
        Monitor<Integer> monitor = new Monitor<>("HoHList", MONITORING_INTERVAL);
        this.hohList = new HoHList<>(monitor, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        new HoHListExperiment().runExperiment(args);
    }

    @Override
    public HoHList<Integer> getList() {
        return this.hohList;
    }
}
