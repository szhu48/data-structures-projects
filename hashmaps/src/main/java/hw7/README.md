# Discussion
From the results below, I deduce that the ChainingHashMap is more efficient and a better
choice than OpenAddressingHashMap. For almost every txt file, ChainingHashMap was quicker.
For my ChainingHashMap, my implementation is a map of LinkedLists. For OpenAddressing,
I implemented the map with linear probing rather than quadratic. Both hashmaps had a load factor
of .75. If that threshold has been reached, the map would rehash and every node would be reinserted.
I implemented the array of primes from the Github for runtime optimization. With the implementation
of the primes list, the collisions can minimized. 

Once again, my data shows that ChainingHashMap is more efficient. I believe this is because for
OpenAddressingHashMap, there could be collisions due to linear probing. Yet, my Open-Addresing
HashMap utilizes less space than my ChainingHashMap. This is probably because for my ChainingHashMap,
my implementation was an array of LinkedLists. Storing the LinkedLists at every index of the array
most likely utilizes a lot of storage which would be very expensive storage wise. So even though
Open-Addressing contains tombstones because the rehashing gets rid of the tombstones, it
seems that it will be lest expensive storage wise than an array of LinkedLists.

Chaining hashmap:
JmhRuntimeTest.buildSearchEngine                                          apache.txt  avgt    2        8832.545           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               apache.txt  avgt    2   303708640.000           bytes
JmhRuntimeTest.buildSearchEngine                                             jhu.txt  avgt    2           0.516           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                  jhu.txt  avgt    2    22677488.000           bytes
JmhRuntimeTest.buildSearchEngine                                          joanne.txt  avgt    2           0.208           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               joanne.txt  avgt    2    22672340.000           bytes
JmhRuntimeTest.buildSearchEngine                                          newegg.txt  avgt    2        4683.655           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               newegg.txt  avgt    2   323219256.000           bytes
JmhRuntimeTest.buildSearchEngine                                       random164.txt  avgt    2         858.044           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc            random164.txt  avgt    2   849408120.000           bytes
JmhRuntimeTest.buildSearchEngine                                            urls.txt  avgt    2           0.105           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                 urls.txt  avgt    2    21967540.000           bytes

Open-addressing hash map:
JmhRuntimeTest.buildSearchEngine                                          apache.txt  avgt    2       87904.005           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               apache.txt  avgt    2   372095704.000           bytes
JmhRuntimeTest.buildSearchEngine                                             jhu.txt  avgt    2           1.049           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                  jhu.txt  avgt    2    21484732.000           bytes
JmhRuntimeTest.buildSearchEngine                                          joanne.txt  avgt    2           0.204           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               joanne.txt  avgt    2    21449932.000           bytes
JmhRuntimeTest.buildSearchEngine                                          newegg.txt  avgt    2        5031.655           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               newegg.txt  avgt    2   203818912.000           bytes
JmhRuntimeTest.buildSearchEngine                                       random164.txt  avgt    2         921.737           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc            random164.txt  avgt    2   820993336.000           bytes
JmhRuntimeTest.buildSearchEngine                                            urls.txt  avgt    2           0.101           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                 urls.txt  avgt    2    21977032.000           bytes