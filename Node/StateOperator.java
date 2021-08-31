package Node;

import Algorithms.AStar;
import Algorithms.Algorithm;
import Algorithms.DFBnB;
import Algorithms.IDAStar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class StateOperator {

    private static Queue<State> _Options;
    private static int row;
    private static int column;
    private static HashMap<String, State> States = new HashMap<>();

    public static Queue<State> Generator(State current, Algorithm some) {

        _Options = new LinkedList<State>();
        row = current.getBoard().length;
        column = current.getBoard()[0].length;
        States.put(current.getKey(), current);

        int i = current.get_Empty()[0].x;
        int j = current.get_Empty()[0].y;
        /*
         * In case we having only One Empty Block
         * */
        if (current.get_Empty()[1] != null) {

            int k = current.get_Empty()[1].x;
            int v = current.get_Empty()[1].y;

            int[] fromWhere = {i, j, k, v};

            if (checkHorizontal(i, j, k, v)) {
                if (i >= 0 && i != row - 1 && k >= 0 && k != row - 1) {
                    int[] whereTo = {i + 1, j, k + 1, v}; /* UP */
                    StateOperator.TwoMove(fromWhere, whereTo, current, "U", some);
                }
                if (i <= row - 1 && i != 0 && k != 0 && k <= row - 1) {
                    int[] whereTo = {i - 1, j, k - 1, v}; /* Down */
                    StateOperator.TwoMove(fromWhere, whereTo, current, "D", some);
                }
            } else if (checkVertical(i, j, k, v)) {
                if (j >= 0 && j != column - 1 && v >= 0 && v != column - 1) {
                    int[] whereTo = {i, j + 1, k, v + 1}; /* Left */
                    StateOperator.TwoMove(fromWhere, whereTo, current, "L", some);
                }
                if (j <= column - 1 && j != 0 && v != 0 && v <= column - 1) {
                    int[] whereTo = {i, j - 1, k, v - 1}; /* Right */
                    StateOperator.TwoMove(fromWhere, whereTo, current, "R", some);
                }
            }
            oneBlock(k, v, current, some);
        }
        oneBlock(i, j, current, some);

        return _Options;
    }

    private static void oneBlock(int i, int j, State current, Algorithm some) {
        String[][] dummy = current.getBoard();

        if (j >= 0 && j != current.getBoard()[0].length - 1) {
            if (!dummy[i][j + 1].equals("_"))
                StateOperator.Move(i, j, i, j + 1, current, "L", some); /* LEFT */
        }
        if (i >= 0 && i != current.getBoard().length - 1) {
            if (!dummy[i + 1][j].equals("_"))
                StateOperator.Move(i, j, i + 1, j, current, "U", some); /* UP */
        }
        if (j <= current.getBoard()[0].length - 1 && j != 0) {
            if (!dummy[i][j - 1].equals("_"))
                StateOperator.Move(i, j, i, j - 1, current, "R", some); /* RIGHT */
        }
        if (i <= current.getBoard().length - 1 && i != 0) {
            if (!dummy[i - 1][j].equals("_"))
                StateOperator.Move(i, j, i - 1, j, current, "D", some); /* DOWN */
        }
    }

    /**
     * Here we are moving the Location where the Empty Block is
     *
     * @param i    - old row
     * @param j    - old column
     * @param x    - new row
     * @param y    - new column
     * @param node - the State
     * @param move - the way me Move - (L,U,R,D)
     */
    private static void Move(int i, int j, int x, int y, State node, String move, Algorithm some) {

        Location[] blocks = new Location[2];
        /**
         * This "for" loop is for us do not mix the 2 Empty Block in keep it in order
         * When the node will be chose ny the Algorithm the 2 Empty blocks will be in the Right place
         */
        for (Location place : node.get_Empty()) {
            if (place.x == i && place.y == j)
                blocks[0] = new Location(x, y);
            else
                blocks[1] = new Location(place.x, place.y);
            if (node.get_Empty()[1] == null) break;
        }
        String[][] dummy = CopyState(node.getBoard());
        StateOperator.Swap(i, j, x, y, dummy);
        move = dummy[i][j] + move;
        State n = new State(dummy, node, blocks, move, 5);
        if (n.equals(n.get_Parent().get_Parent())) return;

        if (some.getClass() == AStar.class ||
                some.getClass() == DFBnB.class ||
                some.getClass() == IDAStar.class) {
            if (States.containsKey(n.getKey())) {
                if (n.getCost() > States.get(n.getKey()).getCost()) {
                    return;
                } else {
                    States.remove(n.getKey());
                    States.put(n.getKey(), n);
                }
            }
        }
        _Options.add(n);

    }

    private static void Swap(int i, int j, int x, int y, String[][] move) {
        String temp = move[i][j];
        move[i][j] = move[x][y];
        move[x][y] = temp;
    }

    private static void TwoMove(int[] from, int[] to, State node, String TODO, Algorithm some) {
        Location[] blocks = new Location[2];
        blocks[0] = new Location(to[0], to[1]);
        blocks[1] = new Location(to[2], to[3]);
        String[][] dummy = CopyState(node.getBoard());
        StateOperator.Swap(from[0], from[1], to[0], to[1], dummy);
        StateOperator.Swap(from[2], from[3], to[2], to[3], dummy);
        String move = "";
        int cost = 0;
        switch (TODO) {
            case "L":
                move = dummy[from[2]][from[3]] + "&" + dummy[from[0]][from[1]] + TODO;
                cost = 6;
                break;
            case "R":
                move = dummy[from[0]][from[1]] + "&" + dummy[from[2]][from[3]] + TODO;
                cost = 6;
                break;
            case "U":
                move = dummy[from[2]][from[3]] + "&" + dummy[from[0]][from[1]] + TODO;
                cost = 7;
                break;
            case "D":
                move = dummy[from[0]][from[1]] + "&" + dummy[from[2]][from[3]] + TODO;
                cost = 7;
                break;
        }
        State n = new State(dummy, node, blocks, move, cost);
        if (n.equals(n.get_Parent().get_Parent())) return;

        if (some.getClass() == AStar.class ||
                some.getClass() == DFBnB.class ||
                some.getClass() == IDAStar.class) {
            if (States.containsKey(n.getKey())) {
                if (n.getCost() > States.get(n.getKey()).getCost()) {
                    return;
                } else {
                    States.remove(n.getKey());
                    States.put(n.getKey(), n);
                }
            }
        }

        _Options.add(n);

    }

    private static String[][] CopyState(String[][] node) {
        String[][] newState = new String[node.length][node[0].length];
        for (int i = 0; i < node.length; i++) {
            for (int j = 0; j < node[0].length; j++) {
                newState[i][j] = node[i][j];
            }
        }
        return newState;
    }

    /**
     * The function check if there is 2 empty blocks next to each other
     * Horizontal and Vertical. if there is, 2 blocks can move in the next step.
     *
     * @param i
     * @param j (i,j) Location of 1 empty
     * @param k
     * @param v (k,v) Location of 2 empty
     * @return
     */
    public static boolean checkHorizontal(int i, int j, int k, int v) {
        return (j + 1 == v || j == v + 1) && (i == k);
    }

    public static boolean checkVertical(int i, int j, int k, int v) {
        return (i + 1 == k || i == k + 1) && (j == v);
    }

}
