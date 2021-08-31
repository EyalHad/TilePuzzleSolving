package Algorithms;

import Node.State;

public interface Algorithm {


    public void SearchSolution(State startState, State goalState);

    public void PrintToScreen(boolean output, State current);

    public boolean CheckGoal(State possible, State goal);

    public void CreatePath(State goal);


}
