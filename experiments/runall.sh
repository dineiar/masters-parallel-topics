#!/usr/bin/env bash

mvn -f ../ package

EXPERIMENTS="CoarseListExperiment HoHListExperiment HoHContainsLockFreeListExperiment"

for exp in $EXPERIMENTS;
do
    OUTPUT="$exp.csv"
    date >> $OUTPUT
    echo -n "Running $exp from 1 to 10..."
    for i in {1..10};
    do
        echo -n " $i"
        java -cp ../target/classes masters.experiment.$exp $i $i $i >> $OUTPUT
    done;
    echo ""
done;