import Algorithms.*;
import InputOutput.Input;
import InputOutput.Output;

public class Ex1 {

    /**
     * Here it all begins,
     * first reading the input file - the Input class analyze the file so we can know with which algorithm the program
     * should work, initial a new Output instance so the Algorithm have the right parameters at the right time
     * send the name of the Algorithm to the SWITCH and the magic happens else where.
     *
     */
    public static void main(String[] args) {

        Input input = new Input("input.txt");
        Output output = new Output(input.is_Print(), input.is_Time());
        switch (input.getAlgorithm()) {

            case "BFS":
                new BFS(input.getGameState(), input.getGameGoal(), output);
                break;
            case "DFID":
                new DFID(input.getGameState(), input.getGameGoal(), output);
                break;
            case "A*":
                new AStar(input.getGameState(), input.getGameGoal(), output);
                break;
            case "IDA*":
                new IDAStar(input.getGameState(), input.getGameGoal(), output);
                break;
            case "DFBnB":
                new DFBnB(input.getGameState(), input.getGameGoal(), output);
                break;

        }
    }
}





