#!/usr/bin/env bash

mvn -f ../ package

MIN=1
MAX=10
EXPERIMENTS="CoarseListExperiment HoHListExperiment"

for exp in $EXPERIMENTS;
do
    OUTPUT="$exp.csv"
    echo -n "Running $exp from $MIN to $MAX..."
    for i in {$MIN..$MAX};
    do
        echo -n " $i"
        java -cp ../target/classes masters.experiment.$exp $i $i $i >> $OUTPUT
    done;
    echo ""
done;