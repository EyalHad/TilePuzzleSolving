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

![CanMoveUp](/images/MoveUp.png) ![CanMoveLeft](/images/MoveLeft.png)






