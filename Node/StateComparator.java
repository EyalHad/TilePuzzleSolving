package Node;

import java.util.Comparator;

public class StateComparator implements Comparator<State> {

    @Override
    public int compare(State a, State b) {
        if (a.getHeuristicValue() + a.getDepth() > (b.getHeuristicValue()) + b.getDepth()) return 1;
        else if (a.getHeuristicValue() + a.getDepth() < (b.getHeuristicValue()) + b.getDepth()) return -1;
        else return Integer.compare(a.ToHere(), b.ToHere());
    }
}
