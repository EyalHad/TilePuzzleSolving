# Introduction to Artificial Intelligence

This program is solving Tile puzzle\
(the input is design to get any size of puzzle but in reality 5X5 if the solution is too complex it won't solve it in human time).

### Tile Puzzle 

![1,2,3,4,5,6,7,8](/images/GoalState.png)

Here represented the Goal State.\
The puzzle is received in a random combination of the numbers above\
the objective is to reach the goal state with a minimum number of steps.

The program also solve the puzzle when there are 2 missing blocks\
if those are intact we can move 2 at the same time (it counts as one step)

> There is a different cost for each move:
> > Moving a single block, cost = 5 (the direction is irrelevant) 
> 
> 2 Block at the same time - if the condition allow it
> > UP || DOWN cost = 7
> 
> > LEFT || RIGHT cost = 6

![CanMoveUp](/images/MoveUp.png) ![Empty](/images/Space.png)![CanMoveLeft](/images/MoveLeft.png)

### Description of the input and output

**each input file should have these attributes:**
* First line should include the name of the Algorithm from this list - { BFS, DFID, IDA*, A*, DFBnB }
* Second line we receive a condition - to print to the Output the time, or not. - { with time, no time }
* Third line another condition - to print to the console the current state that the algorithm works on, or not. - { with open, no open }
* Forth line is the size of the matrix that represent our tile puzzle - uxv where u,v natural integers.
* Until we reach a line that shows `Goal state:` the initial state line by line.
* Line below the `Goal state:` and until EOF, the Goal state is being read line by line.

***Example***
![inputExample](/images/inputEg.png)

**each output file should have these attributes:**






