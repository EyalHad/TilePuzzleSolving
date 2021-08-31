package InputOutput;

import java.io.FileWriter;
import java.io.IOException;

public class Output {

    private static long _Time;
    private static boolean printTime;
    private static String Num;
    private static String Cost;
    private static String Path;
    private boolean _Print;

    public Output(boolean p, boolean time) {
        _Print = p;
        printTime = time;
        if (time) {
            _Time = System.currentTimeMillis();
        }
    }

    public static void fileWrite() {

        try {

            FileWriter file = new FileWriter("output.txt");
            file.write(Path);
            file.write(Num);
            if (Cost != null)
                file.write(Cost);
            if (printTime)
                file.write(is_Time() + " seconds");


            file.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String is_Time() {
        String t = (System.currentTimeMillis() - _Time) / 1_000.0 + "";
        return t;
    }

    public boolean is_Print() {
        return _Print;
    }


    public void setNum(int num) {
        Num = "Num: " + num + "\n";
    }

    public void setCost(int cost) {
        Cost = "Cost: " + cost + "\n";
    }

    public void setPath(String path) {
        Path = path + "\n";
    }
}
