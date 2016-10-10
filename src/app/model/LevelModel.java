package app.model;
import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by Fraser McIntosh on 3/10/2016.
 */
public class LevelModel {
    String _name;
    int _timesCompleted;
    static ArrayList<LevelModel> _levels = new ArrayList<>();
    private static final int MAX_SCORE = 3;
    private String levelState = "Not Max";
    public boolean _starUnlocked = false;
    public boolean _maxScoreReached = false;

    // Set the name of the level and how many times it's been completed
    LevelModel(String name, int timesCompleted) {
        _name = name;
        _timesCompleted = timesCompleted ;
    }


    // Delete the level file
    public static void deleteFile() {
        File file = new File(UtilFile.LEVELS + "");
        file.delete();
        if(file.isFile()) {
            file.delete();
        }
        clearLevels();
    }

    //getters
    public int getTimesCompleted() {
        return this._timesCompleted;
    }

    public boolean getStarUnlocked() {
        return this._starUnlocked;
    }

    public static ArrayList<LevelModel> getLevels() {
        return _levels;
    }

    public int index() {
        return _levels.indexOf(this);
    }

    public int getLevelAsInt() {
        return _levels.indexOf(this) + 1;
    }
    public static LevelModel get(int index ){
        return _levels.get(index);
    }
    public static LevelModel get(String name) throws NoSuchElementException{
        for(LevelModel level : _levels){
            if((level +"").equals(name)) {
                return level;
            }
        }
        throw new java.util.NoSuchElementException();
    }

    // Start up level model
    public static void initialise() {
        createLevels();
    }

    // reset level model
    public static void reset() {
        deleteFile();
        initialise();
    }

    // create level files and lists
    private static void createLevels() {
        BufferedReader in;
        // file de-constructed into lists of levels
        // Create an array representing the current file
        ArrayList<String> highScores = new ArrayList<>();
        try {

            File f = new File(UtilFile.LEVELS + "");
            boolean containsWords = false;
            if(f.isFile()) {
                in = new BufferedReader(new FileReader(UtilFile.LEVELS + ""));
                String currentLine = in.readLine();
                if (currentLine != null) containsWords = true;
            }
            if (!f.isFile() || !containsWords) {
                if (!f.isFile()) {
                    f.createNewFile();
                }
                // file de-constructed into lists of level
                in = new BufferedReader(new FileReader(SpellingListModel.getPath()));

                String currentLine = in.readLine();

                // loop through till end of file

                // to keep track of the right level
                int level = 1;
                while (currentLine != null) {
                    if (currentLine.contains("%")) {
                        _levels.add(new LevelModel(currentLine.split("%")[1], 0));
                    }
                    currentLine = in.readLine();
                }
                in.close();
            }else{
                parseLevels();
            }
            }catch(IOException e){
                e.printStackTrace();
            }

            syncLevels();

    }



    // read in from files
    private static void parseLevels() {
        File file = new File(UtilFile.LEVELS + "");
        BufferedReader in = null;
        // file de-constructed into lists of levels
        ArrayList<LevelModel> fileWords = new ArrayList<>();
        try {
            in = new BufferedReader(new FileReader(file + ""));

            String currentLine = in.readLine();

            // loop through till end of file

            // to keep track of the right level
            while (currentLine != null) {
                LevelModel level = new LevelModel(currentLine.substring(0, currentLine.length() -2),Integer.parseInt(currentLine.charAt(currentLine.length() -1) + ""));
                _levels.add(level);
                currentLine = in.readLine();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        // put all files into a map

    }

    // sync files and levels
    public static void syncLevels() {
        //clear every file
        clearLevelFile();
        //Loop through every file
        File file = new File(UtilFile.LEVELS + "");

        try {
            // make writer
            PrintWriter output = new PrintWriter(new FileWriter(file, true));
            //loop through every file
            for (int i = 0; i < _levels.size(); i++) {
                //write out level header
                LevelModel level = _levels.get(i);
                output.println(level.toString() + " " + level.getTimesCompleted());
            }
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    // clear everything
    public static void clearLevels() {
        clearLevelFile();
        _levels = new ArrayList<>();
    }

    // Give ability to 'level up' if they get a perfect score on the level
    public void nextLevel() {
        _maxScoreReached = false;
        _starUnlocked =false;
        if(_timesCompleted < MAX_SCORE) {
            _timesCompleted++;
            _starUnlocked =true;
            int index = _levels.indexOf(this);
            _levels.remove(index);
            _levels.add(index, this);
            if(_timesCompleted == MAX_SCORE){
                _maxScoreReached = true;
            }
        } else {


        }
        syncLevels();
    }


    // clear the level file
    public static void clearLevelFile() {
        File f = new File(UtilFile.LEVELS + "");
        if (f.isFile()) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(f);
                writer.print("");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
        }

    }

    @Override
    public String toString() {
        return this._name;
    }
}
