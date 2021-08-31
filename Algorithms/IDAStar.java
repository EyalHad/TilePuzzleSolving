package Algorithms;

import InputOutput.Output;
import Node.State;
import Node.StateOperator;

import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;

public class IDAStar implements Algorithm {
    private static HashMap<String, State> _Frontier;
    private Output output;
    private int _Created;

    public IDAStar(State startState, State goalState, Output output) {
        _Frontier = new HashMap<>();
        this.output = output;
        SearchSolution(startState, goalState);
        Output.fileWrite();

    }

    @Override
    public void SearchSolution(State startState, State goalState) {
        _Created = 1;
        boolean stop = false;


        Stack<State> stack = new Stack<State>();
        startState.setDistance(goalState);
        double t = startState.getHeuristicValue();

        while (t < Integer.MAX_VALUE) {

            double minF = Integer.MAX_VALUE;
            stack.push(startState);
            _Frontier.put(startState.getKey(), startState);

            while (!stack.empty()) {
                if (stop) break;
                State current = stack.pop();
                PrintToScreen(output.is_Print(), current);

                if (current.Mark().equals("out")) {

                    _Frontier.remove(current.getKey());

                } else {

                    current.setMark("out");
                    stack.add(current);
//                    HashSet<String> dummy = new HashSet<>();
                    Queue<State> options = StateOperator.Generator(current, this);
                    _Created += options.size();

                    while (!options.isEmpty()) {

                        State possible = options.poll();
                        possible.setDistance(goalState);
                        int f = (int) possible.getHeuristicValue() + possible.getCost();

                        if (f > t) {
                            minF = Math.min(f, minF);
                            continue;
                        }

                        if (_Frontier.containsKey(possible.getKey()) && possible.Mark().equals("out")) {
                            continue;
                        }

                        if (_Frontier.containsKey(possible.getKey()) && !possible.Mark().equals("out")) {

                            double left = _Frontier.get(possible.getKey()).getCost();
                            double right = possible.getCost();

                            if (left > right) {
                                _Frontier.remove(possible.getKey());
                                stack.remove(possible);
                            } else continue;
                        }
                        if (CheckGoal(possible, goalState)) {
                            CreatePath(possible);
                            t = Integer.MAX_VALUE;
                            stop = true;
                            break;
                        }
                        _Frontier.put(possible.getKey(), possible);
                        stack.add(possible);
                    }
                }
            }
            t = minF;
            startState.setMark("AGAIN");
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
