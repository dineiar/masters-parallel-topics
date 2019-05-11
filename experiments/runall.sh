#!/usr/bin/env bash

mvn -f ../ package

# EXPERIMENTS="CoarseListExperiment HoHListExperiment HoHContainsLockFreeListExperiment"
EXPERIMENTS="RandomCoarseListExperiment RandomHoHListExperiment RandomHoHContainsLockFreeListExperiment RandomLazyListExperiment RandomOptimisticListExperiment"
IS_RANDOM=true

for exp in $EXPERIMENTS;
do
    OUTPUT="$exp.csv"
    date >> $OUTPUT
    if [ $IS_RANDOM == true ]; then
        echo -n "Running $exp from 2 to 20..."
        for i in {2..20..2};
        do
            echo -n " $i"
            java -cp ../target/classes masters.experiment.$exp $i >> $OUTPUT
        done;
    else
        echo -n "Running $exp from 1 to 10..."
        for i in {1..10};
        do
            echo -n " $i"
            java -cp ../target/classes masters.experiment.$exp $i $i $i >> $OUTPUT
        done;
    fi;
    echo ""
done;