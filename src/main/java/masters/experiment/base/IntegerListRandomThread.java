package masters.experiment.base;

import java.util.concurrent.ThreadLocalRandom;

import masters.listset.base.IList;

public class IntegerListRandomThread<T extends IList<Integer>> extends IntegerListThread<T> {

    public IntegerListRandomThread(T list, Integer waitInterval) {
        super(list, null, waitInterval);
    }
    public IntegerListRandomThread(T list, Operation operation, Integer waitInterval) {
        super(list, operation, waitInterval);
    }

    public int getRandom() {
        return ThreadLocalRandom.current().nextInt(BaseIntegerExperiment.MIN_VALUE, BaseIntegerExperiment.MAX_VALUE + 1);
    }

    @Override
    public void run() {
        while (this.running) {
            int operationId = ThreadLocalRandom.current().nextInt(0, Operation.values().length);
            this.operation = Operation.values()[operationId];
            doOperation();
        }
    }
}