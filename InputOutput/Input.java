package InputOutput;

import Node.Location;
import Node.State;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Input {
    private Location[] emptyBlocks = new Location[2];

    private ArrayList<String> inputs = new ArrayList<>();

    private String algorithm;

    private boolean _Time;
    private boolean _Print;

    private int _N;
    private int _M;

    private State gameState;
    private State gameGoal;

    public Input(String input) {
        fileRead(input);
        SetArguments();
    }

    private void fileRead(String input) {

        try {

            File file = new File(input);
            Scanner myReader = new Scanner(file);

            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                inputs.add(data);
            }

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private String[][] StateGenerate(int game) {
        String[][] state = new String[_N][_M];
        int count = 0;
        int index = game;
        int i = 0;
        while (i < _N) {

            String line = inputs.get(index);
            String[] temp = line.split(",");

            for (int j = 0; j < _M; j++) {
                state[i][j] = temp[j];
                if (temp[j].equals("_")) {
                    count++;
                    if (count == 1) {
                        emptyBlocks[0] = new Location(i, j);
                    } else if (count == 2) {
                        emptyBlocks[1] = new Location(i, j);


                    }
                }
            }
            index++;
            i++;
        }

        return state;
    }

    private void SetArguments() {
        algorithm = inputs.get(0);
        _Time = inputs.get(1).equals("with time");
        _Print = inputs.get(2).equals("with open");
        int i = 3;
        String[] N_M = inputs.get(3).split("x");
        _N = Integer.parseInt(N_M[0]);
        _M = Integer.parseInt(N_M[1]);
        String[][] temp = StateGenerate(4);
        gameState = new State(temp, null, emptyBlocks, null, 0);

        String[][] temp2 = StateGenerate(_N + 5);
        gameGoal = new State(temp2, null, emptyBlocks, null, 0);


    }

    public String getAlgorithm() {
        return algorithm;
    }

    public boolean is_Time() {
        return _Time;
    }

    public boolean is_Print() {
        return _Print;
    }

    public State getGameState() {
        return gameState;
    }

    public State getGameGoal() {
        return gameGoal;
    }
}
