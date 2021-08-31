package Node;

import Algorithms.HeuristicFunc;

public class State {

    private static HeuristicFunc heuristic = new HeuristicFunc();
    private final int toHere;
    private String move;
    private State _Parent;
    private String[][] _Matrix;
    private Location[] _Empty;
    private int _Depth;
    private String key;
    private int cost = 0;
    private double _Distance;
    private String _Mark = "";

    public State(String[][] n, State p, Location[] location, String move, int c) {
        CopyPointArray(location);
        this._Parent = p;
        setState(n);
        setMove(move);
        setDepth();
        toHere = c;
        if (c != 0) {
            cost += c + p.cost;
        }
    }

    private void CopyPointArray(Location[] arr) {
        _Empty = new Location[arr.length];
        _Empty[0] = new Location(arr[0].x, arr[0].y);
        if (arr[1] != null) {
            _Empty[1] = new Location(arr[1].x, arr[1].y);
        }

    }

    private void setState(String[][] node) {
        StringBuilder k = new StringBuilder();
        _Matrix = new String[node.length][node[0].length];
        for (int i = 0; i < node.length; i++) {
            for (int j = 0; j < node[0].length; j++) {
                _Matrix[i][j] = node[i][j];
                k.append("[");
                k.append(node[i][j]);
                k.append("]");
                k.append(" ");
            }
            k.append("\n");
        }
        key = k.toString();
    }

    public String getKey() {
        return key;
    }

    public Location[] get_Empty() {
        return _Empty;
    }

    public State get_Parent() {
        return _Parent;
    }

    public void set_Parent(State _Parent) {
        this._Parent = _Parent;
    }

    public String[][] getBoard() {
        return _Matrix;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public int getCost() {
        return cost;
    }

    private void setDepth() {
        if (_Parent == null) {
            _Depth = 0;
        } else {
            _Depth = _Parent._Depth + 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return key.equals(state.key);
    }

    @Override
    public String toString() {
        return key + ",\n";
    }

    public void setDistance(State goal) {
        this._Distance = heuristic.HeuristicValue(this, goal);
    }

    public double getHeuristicValue() {
        return _Distance;

    }

    public String Mark() {
        return _Mark;
    }

    public void setMark(String _Mark) {
        this._Mark = _Mark;
    }


    public int getDepth() {
        return _Depth;
    }

    public int ToHere() {
        return toHere;
    }
}
