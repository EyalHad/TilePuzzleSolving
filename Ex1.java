import Algorithms.*;
import InputOutput.Input;
import InputOutput.Output;

public class Ex1 {


    public static void main(String[] args) {

        Input input = new Input("input.txt");
        Output output = new Output(input.is_Print(), input.is_Time());
        switch (input.getAlgorithm()) {

            case "BFS":
                Algorithm bfs = new BFS(input.getGameState(), input.getGameGoal(), output);
                break;
            case "DFID":
                Algorithm DFID = new DFID(input.getGameState(), input.getGameGoal(), output);
                break;
            case "A*":
                Algorithm AStar = new AStar(input.getGameState(), input.getGameGoal(), output);
                break;
            case "IDA*":
                Algorithm IDAStar = new IDAStar(input.getGameState(), input.getGameGoal(), output);
                break;
            case "DFBnB":
                Algorithm DFBnB = new DFBnB(input.getGameState(), input.getGameGoal(), output);
                break;

        }
    }
}





