# masters-parallel-topics

Compile with 
```bash
$ mvn package
```

Run the experiment with 
```bash
$ java -cp target/classes masters.experiment.CoarseListExperiment 2 2 2
$ java -cp target/classes masters.experiment.HoHListExperiment 2 2 2
```
Where the class is the experiment and the numbers are the number of threads performing add, remove and contains, respectively.

To build and run all experiments at multiple degrees of parallelism and record results on CSV format:
```bash
$ cd experiments
$ ./runall.sh
```

### Refs

* https://www.devmedia.com.br/threads-paralelizando-tarefas-com-os-diferentes-recursos-do-java/34309