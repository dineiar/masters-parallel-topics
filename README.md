# masters-parallel-topics

Compile with 
```bash
$ mvn package
```

Test with 
```bash
$ java -cp target/classes masters.experiment.CoarseListExperiment 2 2 2
```

Where the class is the experiment and the numbers are the number of threads performing add, remove and contains, respectively.