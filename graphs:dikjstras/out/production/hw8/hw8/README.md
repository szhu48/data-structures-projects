# Homework 8

## Discussion 

After running these tests, I can see that the SystemRunTimeTest outcome were roughly the same for all 
three locations. In terms of memoryMonitorTest, the used memory were similar as well. This was really 
interesting. One of the reasons could be because that my implementation of SparseGraph uses HashSets 
so accessing and finding objects are O(1).
Additionally, in my vertexnode class, I also stored incoming and outgoing edges, so accessing
and finding edges are also O(1). Therefore, the performance overall does not change for different
endpoints. Looking at the JMHRuntimeTest, I see that the score is relatively the same for all three locations.
The only difference is the margin of error of JHU to Druid Lake is slightly larger than the others. Additionally,
my DijkstraStreetSearcher uses a hashset as well to track whether a vertex is explored.


JHU to Druid Lake
~~~ SystemRuntimeTest ~~~
Config: baltimore.streets.txt from -76.6175,39.3296 to -76.6383,39.3206
Loading network took 82 milliseconds.
Finding shortest path took 21 milliseconds.
~~~~~~     END     ~~~~~~

~~~ MemoryMonitorTest ~~~
Config: baltimore.streets.txt from -76.6175,39.3296 to -76.6383,39.3206
	Used memory: 11398.66 KB (Δ = 0.000)
Instantiating empty Graph data structure
Instantiating empty StreetSearcher object
	Used memory: 11621.28 KB (Δ = 222.625)
Loading the network
	Used memory: 22924.69 KB (Δ = 11303.406)
Finding the shortest path
	Used memory: 24077.87 KB (Δ = 1153.180)
Setting objects to null (so GC does its thing!)
	Used memory: 13483.35 KB (Δ = -10594.516)
~~~~~~     END     ~~~~~~

7-11 to Druid Lake
Config: baltimore.streets.txt from -76.6214,39.3212 to -76.6383,39.3206
Loading network took 81 milliseconds.
Finding shortest path took 20 milliseconds.

~~~ MemoryMonitorTest ~~~
Config: baltimore.streets.txt from -76.6214,39.3212 to -76.6383,39.3206
	Used memory: 11406.79 KB (Δ = 0.000)
Instantiating empty Graph data structure
Instantiating empty StreetSearcher object
	Used memory: 11643.56 KB (Δ = 236.773)
Loading the network
	Used memory: 22962.86 KB (Δ = 11319.297)
Finding the shortest path
	Used memory: 24103.73 KB (Δ = 1140.875)
Setting objects to null (so GC does its thing!)
	Used memory: 13489.91 KB (Δ = -10613.828)
~~~~~~     END     ~~~~~~


Inner Harbor to JHU
~~~ SystemRuntimeTest ~~~
Config: baltimore.streets.txt from -76.6107,39.2866 to -76.6175,39.3296
Loading network took 80 milliseconds.
Finding shortest path took 21 milliseconds.
~~~~~~     END     ~~~~~~

~~~ MemoryMonitorTest ~~~
Config: baltimore.streets.txt from -76.6107,39.2866 to -76.6175,39.3296
	Used memory: 11411.31 KB (Δ = 0.000)
Instantiating empty Graph data structure
Instantiating empty StreetSearcher object
	Used memory: 11640.79 KB (Δ = 229.477)
Loading the network
	Used memory: 22956.79 KB (Δ = 11316.000)
Finding the shortest path
	Used memory: 24108.40 KB (Δ = 1151.609)
Setting objects to null (so GC does its thing!)
	Used memory: 13495.38 KB (Δ = -10613.016)
~~~~~~     END     ~~~~~~
x
JmhRuntimeTest.findShortestPath                                        7-11 to Druid Lake  avgt   20          9.471 ±         0.038   ms/op
JmhRuntimeTest.findShortestPath                                         JHU to Druid Lake  avgt   20          9.635 ±         0.283   ms/op
JmhRuntimeTest.findShortestPath                                       Inner Harbor to JHU  avgt   20          9.480 ±         0.080   ms/op