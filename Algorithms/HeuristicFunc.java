package Algorithms;

import Node.Location;
import Node.State;
import Node.StateOperator;

public class HeuristicFunc {

    public double HeuristicValue(State initialState, State goalState) {
        String[][] initialBoard = initialState.getBoard();
        String[][] goalBoard = goalState.getBoard();
        int r = initialBoard.length;
        int c = initialBoard[0].length;

        double cost = 7;
        double diff = 0;

        double div = 5;
        if (initialState.get_Empty()[1] != null) {
            if (initialState.get_Parent() == null) {
                cost = 4.5;
            }

            int i = initialState.get_Empty()[0].x;
            int j = initialState.get_Empty()[0].y;
            int k = initialState.get_Empty()[1].x;
            int v = initialState.get_Empty()[1].y;
            if (StateOperator.checkHorizontal(i, j, k, v)) {
                cost = 6;

            } else if (StateOperator.checkVertical(i, j, k, v)) {
                cost = 5;

            }
        }


        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++) {
                if (!initialBoard[i][j].equals("_")) {
                    Location point = compareToGoal(initialBoard[i][j], goalBoard, r, c);
                    diff += (Math.abs(i - point.x) + Math.abs(j - point.y)) * cost;
                } else {
                    Location point = compareToGoal(initialBoard[i][j], goalBoard, r, c);
                    diff += (Math.abs(i - point.x) + Math.abs(j - point.y));
                }
            }

        return (diff / div);
    }

    private Location compareToGoal(String digit, String[][] goalBoard, int rows, int columns) {
        Location index = new Location(0, 0);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                if (digit.equals(goalBoard[i][j])) {
                    index = new Location(i, j);
                    return index;
                }
        return index;
    }
}