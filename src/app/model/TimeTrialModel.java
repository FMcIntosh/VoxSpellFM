package app.model;

import java.io.*;
import java.util.ArrayList;


/*
 * Created by Fraser McIntosh on 6/10/2016.
 *
 * Models a time trial
 */
public class TimeTrialModel extends QuizModel {
    private static final int NUM_WORDS = 300;

    public TimeTrialModel(LevelModel levelSelected) {
        super(false, levelSelected, NUM_WORDS);
    }

    //Initialises the timetrial file
    public static void initialise() {
        File f = new File(UtilFile.TIMETRIAL + "");
        // If we don't have this file, then create it
        if (!f.isFile()) {
            try {
                PrintWriter output = new PrintWriter(new FileWriter(f, true));
                f.createNewFile();
                ArrayList<LevelModel> levels = LevelModel.getLevels();
                for (int i = 0; i < levels.size(); i++) {

                    // Print NA for high score
                    output.println(0);

                }
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

    // resets the time trial file
    public static void reset() {
        File file = new File(UtilFile.TIMETRIAL + "");
        file.delete();
        initialise();
    }


    // Update the high score for a level, then re-print all the high scores
    private static void updateLevelHighScore(int newHighScore, LevelModel level) {
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
            PrintWriter output = new PrintWriter(new FileWriter(UtilFile.TIMETRIAL + ""));
            // print back in to file

            for (String s : highScores) {
                // Print NA for high score
                output.println(s);
            }
            output.close();
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
            in.close();

        } catch (Exception e) {

        }
        return Integer.parseInt(currentLine);
    }

    // Check if new high score is actually greater than the old one
    public static boolean updateHighScore(int newHS, LevelModel level) {
        int currentHS =getHighScoreAtLevel(level);
        if(newHS > currentHS) {
            updateLevelHighScore(newHS, level);
            return true;
        }
        return false;
    }

}
