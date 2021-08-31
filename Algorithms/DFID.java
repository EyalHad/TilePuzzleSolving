package Algorithms;

import InputOutput.Output;
import Node.State;
import Node.StateOperator;

import java.util.HashSet;
import java.util.Queue;
import java.util.Stack;

public class DFID implements Algorithm {

    private static HashSet<String> _Frontier;
    private Output output;
    private int _Created;
    private boolean stop = false;
    private String _Result;

    public DFID(State startState, State goalState, Output output) {
        this.output = output;
        SearchSolution(startState, goalState);
        Output.fileWrite();
    }

    @Override
    public void SearchSolution(State startState, State goalState) {
        _Created = 1;
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            _Frontier = new HashSet<>();
            _Result = Limited_DFS(startState, goalState, i, _Frontier);
            if (!_Result.equals("cutOff")) {
                return;
            }
        }
    }

    private String Limited_DFS(State possible, State goal, int limit, HashSet<String> openList) {
        PrintToScreen(output.is_Print(), possible);
        if (CheckGoal(possible, goal)) {
            CreatePath(possible);
            return _Result;
        } else if (limit == 0) return "cutOff";
        else {
            openList.add(possible.getKey());
            boolean isCutoff = false;
            Queue<State> options = StateOperator.Generator(possible, this);
            _Created += options.size();
            while (!options.isEmpty()) {
                State p = options.poll();
                if (!openList.contains(p.getKey())) {
                    String result = Limited_DFS(p, goal, limit - 1, openList);
                    if (result.equals("cutOff")) {
                        isCutoff = true;
                    } else if (!result.equals("fail")) {
                        return result;
                    }
                }
            }
            openList.remove(possible.getKey());
            if (isCutoff) {
                return "cutOff";
            } else return "fail";
        }
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
        _Result = solution;
        output.setNum(_Created);
        output.setCost(cost);
        output.setPath(solution);
    }

    @Override
    public void PrintToScreen(boolean output, State current) {
        if (output)
            System.out.println(current.getKey());

    }
}


