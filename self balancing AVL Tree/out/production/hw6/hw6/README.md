# Discussion

## Unit testing TreapMap

For Unit testing TreapMap, one of the difficulties was determining how to
rotate based on the priorities the seed value provided. Since the seed values were
set, it was difficult to rotate. For example, my seed value provided a negative
integer as first priority value to distribute. The five values following were
positive integers. It wasn't until the seventh value did the seed value distribute
another negative integer. If I were to insert six values, unless I remove the root,
it would not involve a rotations on the root node. When unit testing, with the random
class seed value, I had to arrange my values around the priorities to fit the rotations
I wanted. In the removeLeftRightRotation and removeRightLeftRotation, I basically implemented
the same tree structure, but just mirrored the tree structure. To elaborate,
for my removeLeftRightRotation, I inserted a tree that was heavier on the right while for
my removeRightLeftRotation, I mirrored the structure and have it be heavier on the left side.
So when I remove the nodes, rather than doing a left rotation, it would do a right rotation and vice versa. 




## Benchmarking
After running the moby_dick.txt file, the results were that the scores of ArrayMap was 2509.031, 
avlTreeMap was 98.172, bstMap was 101.139, and treapMap was 118.797. After running the hotel_california.txt, 
the results were that the scores of ArrayMap had a score of .156, avlTreeMap had a score of .116, bstMap had a score
of .118, and treapMap had a score of .143. 

Running pride and prejudice, avlTreeMap was 55.287 while bstMap was 55.057. Running 
the federalist01.txt, the score for arrayMap was 1.567, avlTreeMap was 0.630, bstMap was .616, 
and treapMap was 0.770. These were cases where bstMap was more efficient than avlTreeMap however, 
not by a significant amount. 

From the data, I can see that avlTreeMap is a very efficient tree. When it was more efficient than other
data structures, it was significantly more efficient. When another data structure was more efficient,
the avlTree was not significantly inefficient. Even though avlTree needs to take the time and space
to calculate balanceFactor, because the tree is balanced, the avlTree is significantly quicker when
searching.