package masters.experiment;

import masters.listset.CoarseList;

public class Run {

    // public CoarseList<Integer> coarseList = new CoarseList<>(Integer.MIN_VALUE, Integer.MAX_VALUE);

    public static void main(String[] args) {
        CoarseList<Integer> coarseList = new CoarseList<>(Integer.MIN_VALUE, Integer.MAX_VALUE);

        Integer item;

        item = 10;
        coarseList.add(item);

        System.out.println("It works!");
    }
}
