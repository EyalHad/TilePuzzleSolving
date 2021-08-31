package Algorithms;

import InputOutput.Output;
import Node.State;
import Node.StateComparator;
import Node.StateOperator;

import java.util.*;

public class DFBnB implements Algorithm {
    private static HashMap<String, State> _Frontier;
    private static ArrayList<State> result;
    private Output output;
    private int _Created;

    public DFBnB(State startState, State goalState, Output output) {
        _Frontier = new HashMap<>();
        this.output = output;
        SearchSolution(startState, goalState);
        Output.fileWrite();
    }

    @Override
    public void SearchSolution(State startState, State goalState) {
        _Created = 1;
        boolean stop = false;
        result = new ArrayList<>();
        Stack<State> stack = new Stack<>();
        _Frontier.put(startState.getKey(), startState);
        stack.add(startState);
        startState.setDistance(goalState);
        double t = startState.getHeuristicValue() * 7.5;

        while (!stack.empty()) {
            if (stop) break;

            State n = stack.pop();
            PrintToScreen(output.is_Print(), n);

            if (n.Mark().equals("out")) {
                _Frontier.remove(n);

            } else {
                n.setMark("out");
                Queue<State> queue = StateOperator.Generator(n, this);
                ArrayList<State> options = new ArrayList<>();
                options.addAll(queue);
                for (State set : options) {
                    set.setDistance(goalState);
                }
                options.sort(new StateComparator()::compare);
                ArrayList<State> copy = new ArrayList<>(options);
                _Created += options.size();

                for (State possible : copy) {
                    possible.setDistance(goalState);
                    int fg = (int) possible.getHeuristicValue() + possible.getCost();
                    if (fg >= t) {
                        for (State toRm : copy) {
                            int tempV = (int) toRm.getHeuristicValue() + toRm.getCost();
                            if (tempV >= t) {
                                options.remove(toRm);
                            }
                        }
                    } else if (_Frontier.containsKey(possible.getKey()) && possible.Mark().equals("out")) {
                        options.remove(possible);
                    } else if (_Frontier.containsKey(possible.getKey()) && !possible.Mark().equals("out")) {
                        double left = _Frontier.get(possible.getKey()).getCost();
                        double right = possible.getCost();

                        if (left <= right) {
                            options.remove(possible);
                        } else {
                            _Frontier.remove(possible.getKey());
                            stack.remove(possible);
                        }
                    } else if (CheckGoal(possible, goalState)) {
                        CreatePath(possible);
                        t = possible.getCost();
                        for (State path : stack) {
                            if (path.Mark().equals("out"))
                                result.add(path);
                        }
                        options.removeIf(state -> options.indexOf(possible) < options.indexOf(possible));
                        options.remove(possible);
                        stop = true;

                    }
                    Collections.reverse(options);
                    stack.addAll(options);
                    for (State toPut : options) {
                        _Frontier.put(toPut.getKey(), toPut);
                    }
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

