package Algorithms;

import InputOutput.Output;
import Node.State;
import Node.StateOperator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFS implements Algorithm {

    private static HashSet<String> _Frontier;
    private static HashSet<String> _Explored;

    private Output output;
    private int _Created;

    public BFS(State startState, State goalState, Output output) {

        _Frontier = new HashSet<>();
        _Explored = new HashSet<>();
        this.output = output;
        SearchSolution(startState, goalState);
        Output.fileWrite();

    }


    @Override
    public void SearchSolution(State startState, State goalState) {
        _Created = 0;
        boolean stop = false;

        Queue<State> queue = new LinkedList<>();
        queue.add(startState);
        _Frontier.add(startState.getKey());

        while (!queue.isEmpty() && !stop) {

            State current = queue.poll();
            _Frontier.remove(current.getKey());
            _Explored.add(current.getKey());

            PrintToScreen(output.is_Print(), current);

            Queue<State> options = StateOperator.Generator(current, this);
            _Created += options.size();

            while (!options.isEmpty()) {

                State possible = options.poll();

                if (!(_Explored.contains(possible.getKey())) &&
                        !(_Frontier.contains(possible.getKey()))) {

                    if (CheckGoal(possible, goalState)) {

                        CreatePath(possible);
                        stop = true;
                        break;
                    }

                    queue.add(possible);
                    _Frontier.add(possible.getKey());

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
        if (output) System.out.println(current.getKey());
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


