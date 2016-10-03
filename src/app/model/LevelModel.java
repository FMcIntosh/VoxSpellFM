package app.model;

import app.AppModel;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Fraser McIntosh on 3/10/2016.
 */
public class LevelModel {
    String _name;
    int _timesCompleted;
    static ArrayList<LevelModel> _levels = new ArrayList<>();

    LevelModel(String name, int timesCompleted) {
        _name = name;
        _timesCompleted = timesCompleted ;
    }

    public int getTimesCompleted() {
        return this._timesCompleted;
    }


    public static void initialise() {
        createLevels();
        parseLevels();
    }

    private static void createLevels() {
        for (UtilFile filename : UtilFile.values()) {
            File f = new File(filename + "");
            if (!f.isFile()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void parseLevels() {
        File file = new File(UtilFile.LEVELS + "");
        BufferedReader in;
        // file de-constructed into lists of levels
        ArrayList<LevelModel> fileWords = new ArrayList<>();
        try {
            in = new BufferedReader(new FileReader(file + ""));

            String currentLine = in.readLine();

            // loop through till end of file

            // to keep track of the right level
            while (currentLine != null) {
                String[] lineSplit = currentLine.split(" ");
                LevelModel level = new LevelModel(lineSplit[0], Integer.parseInt(lineSplit[1]));
                _levels.add(level);
                currentLine = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // put all files into a map

    }

    public static void syncLevels()  {
    //clear every file
    clearLevelFile();
    //Loop through every file
    File file = new File(UtilFile.LEVELS + "");

    try {
        // make writer
        PrintWriter output = new PrintWriter(new FileWriter(file, true));
        //loop through every file
        for(int i =0; i < _levels.size(); i++){
            //write out level header
            LevelModel level = _levels.get(i);
            output.println(level.toString() +" " + level.getTimesCompleted());
        }
        output.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    public void updateLevel(LevelModel level, int timesCompleted) {
        int index = _levels.indexOf(level);
        _levels.add(index, new LevelModel(level + "", timesCompleted));
        updateLevels();
    }

    public static void clearLevels() {
        clearLevelFile();
        _levels = new ArrayList<>();
    }

    public static void clearLevelFile() {
        File f = new File(UtilFile.LEVELS + "");
        if (f.isFile()) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(f);
                // writer.print("");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
        }

    }

    public static void updateLevels() {

    }
    @Override
    public String toString() {
        return this._name;
    }
}
