package app.model;

import app.AppModel;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/*
 * Created by Fraser McIntosh on 6/10/2016.
 */
public class TimeTrialModel extends QuizModel {
    private static final int NUM_WORDS = 200;

    public TimeTrialModel(LevelModel levelSelected) {
        super(false, levelSelected, NUM_WORDS);
    }

    public static void initialise() {
        File f = new File(UtilFile.TIMETRIAL + "");
        // If we don't have this file, then create it
        if (!f.isFile()) {
            try {
                f.createNewFile();
                ArrayList<LevelModel> levels = LevelModel.getLevels();
                for (int i = 0; i < levels.size(); i++) {
                    PrintWriter output = new PrintWriter(new FileWriter(f, true));
                    // Print NA for high score
                    output.println(-1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void reset() {
        File file = new File(UtilFile.TIMETRIAL + "");
        file.delete();
        initialise();
    }


    public static void updateLevelHighScore(int newHighScore, LevelModel level) {
        BufferedReader in;
        // file de-constructed into lists of levels
        // Create an array representing the current file
        ArrayList<String> highScores = new ArrayList<>();
        try {
            in = new BufferedReader(new FileReader(UtilFile.TIMETRIAL + ""));
            String currentLine = in.readLine();
            while (currentLine != null) {
                highScores.add(currentLine);
                currentLine = in.readLine();
            }

            // update correct level
            highScores.set(level.getLevelAsInt() - 1, newHighScore + "");

            // print back in to file
            for (String s : highScores) {
                PrintWriter output = new PrintWriter(new FileWriter(UtilFile.TIMETRIAL + "", true));
                // Print NA for high score
                output.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getHighScoreAtLevel(LevelModel level) {
        BufferedReader in;
        // file de-constructed into lists of levels
        // Create an array representing the current file
        String currentLine = "";
        try {
            in = new BufferedReader(new FileReader(UtilFile.TIMETRIAL + ""));
            for (int i = 0; i < level.getLevelAsInt(); i++) {
                currentLine = in.readLine();
            }


        } catch (Exception e) {

        }
        return Integer.parseInt(currentLine);

    }
}
