package Algorithms;

import InputOutput.Output;
import Node.State;
import Node.StateComparator;
import Node.StateOperator;

import java.util.*;

public class AStar implements Algorithm {
    private static HashMap<String, State> _Frontier;
    private static HashSet<String> _Explored;
    private Output output;
    private int _Created;

    public AStar(State startState, State goalState, Output output) {
        _Frontier = new HashMap<>();
        _Explored = new HashSet<>();
        this.output = output;
        SearchSolution(startState, goalState);
        Output.fileWrite();
    }

    @Override
    public void SearchSolution(State startState, State goalState) {
        boolean stop = false;
        PriorityQueue<State> queue = new PriorityQueue<State>(new StateComparator());
        queue.add(startState);
        _Frontier.put(startState.getKey(), startState);
        _Created = 0;
        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (CheckGoal(current, goalState)) {
                CreatePath(current);
                stop = true;
                break;
            }
            _Explored.add(current.getKey());
            PrintToScreen(output.is_Print(), current);
            Queue<State> options = StateOperator.Generator(current, this);
            _Created += options.size();
            while (!options.isEmpty()) {
                State possible = options.poll();
                if (!(_Explored.contains(possible.getKey())) &&
                        !(_Frontier.containsKey(possible.getKey()))) {
                    possible.setDistance(goalState);
                    queue.add(possible);
                    _Frontier.put(possible.getKey(), possible);
                } else if (queue.contains(possible) && _Frontier.containsKey(possible.getKey())
                        && _Frontier.get(possible.getKey()).getCost() > possible.getCost()) {
                    queue.remove(possible);
                    queue.add(possible);
                    _Frontier.put(possible.getKey(), possible);
                }
            }
        }
        if (!stop) {
            output.setNum(_Created);
            output.setPath("no path");
        }

    }


    @Override
    public void PrintToScreen(boolean output, State current) {
        if (output)
            System.out.println(current.getKey());

    }

    @Override
    public boolean CheckGoal(State possible, State goal) {
        return possible.getKey().equals(goal.getKey());
    }

    @Override
    public void CreatePath(State goal) {

        int cost = goal.getCost();
        Stack<String> stringStack = new Stack<>();
        while (goal.get_Parent() != null) {
            stringStack.push(goal.getMove());
            goal = goal.get_Parent();
        }
        String solution = "";

        while (!stringStack.empty()) {
            solution += stringStack.pop();
            solution += "-";
        }
        solution = solution.substring(0, solution.length() - 1);
        output.setNum(_Created);
        output.setCost(cost);
        output.setPath(solution);
    }
}
