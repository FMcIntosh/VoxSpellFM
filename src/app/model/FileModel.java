package app.model;

import app.AppModel;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Fraser McIntosh on 16/09/2016.
 */
public class FileModel {

    //Stores Words
    static HashMap<String, HashMap<Integer, ArrayList<String>>> _fileMap = new HashMap<>();

    static private ArrayList<String> getLevelWords(String path, int level) {
        HashMap<Integer, ArrayList<String>> fileWords =_fileMap.get(path);
        // Add logic to check that the file has been made
        if(fileWords.containsKey(level)) {
            return fileWords.get(level);
        } else {
            ArrayList<String> emptyList = new ArrayList<>();
            fileWords.put(level, emptyList);
            return getLevelWords(path, level);
        }
    }

    // Creates files that don't already exist and also parses files into _fileMap for
    // easy access during application
    public static void initialise() {
        createFiles();
        parseFiles();
    }


    // Helper method to create files that don't already exist
    private static void createFiles() {
        for (WordFile filename : WordFile.values()) {
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

    public static int calcNumLevels() {
        File file = new File(SpellingListModel.getPath());
        BufferedReader in;
        int level = 0;
        try {
            in = new BufferedReader(new FileReader(file + ""));

            String currentLine = in.readLine();

            // loop through till end of file

            // to keep track of the right level

            while (currentLine != null) {
                if(currentLine.contains("%")){
                    level++;
                }
                currentLine = in.readLine();
            }
        }
            catch (IOException e) {
                e.printStackTrace();
            }
           return level;

    }


    /*
     * Helper method that parses the files and converts them into a more easily
     * read format. Need to parse every time application is started
     * Coupled to format of text file
     */

    private static void parseFile(String path) {
        File file = new File(path);
        BufferedReader in;
        // file de-constructed into lists of levels
        HashMap<Integer, ArrayList<String>> fileWords = new HashMap<>();
        try {
            in = new BufferedReader(new FileReader(file + ""));

            String currentLine = in.readLine();

            // loop through till end of file

            // to keep track of the right level
            int level = 1;
            while (currentLine != null) {
                currentLine = in.readLine();



                // construct levels between each $Level, relying on indexing to store lists in right location
                ArrayList<String> levelWords = new ArrayList<>();
                while (currentLine != null && currentLine.charAt(0) != '%') {
                    levelWords.add(currentLine);
                    currentLine = in.readLine();
                }


                // add levels to construct file
                fileWords.put(level, levelWords);
                // next level's words
                level++;


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // put all files into a map
        _fileMap.put(path, fileWords);
    }
    private static void parseFiles() {
        //Loop through every file
        for (WordFile filename : WordFile.values()) {
            parseFile(filename + "");
        }
        parseFile(AppModel.getSpellingListPath());
    }


    // Sync files with file map incase words have been added
    // that are not on file
    public static void SyncFile(String path) {
       //clear every file
        clearFile(path);
        //Loop through every file
        File file = new File(path);
        PrintWriter output;

        try {
            // make writer
            output = new PrintWriter(new FileWriter(file, true));
            HashMap<Integer, ArrayList<String>> fileWords =  _fileMap.get(path);

            //loop through every file
            for(int level = 1; level <= AppModel.getNumLevels(); level++){
                //write out level header
                output.println("%Level " + (level));

                // if level exists
                if(fileWords.containsKey(level)) {
                    ArrayList<String> levelWords = fileWords.get(level);

                    //write each word
                    for (String word : levelWords) {
                        output.println(word);
                    }
                }

            }
            output.close();

        } catch (IOException e){
            e.printStackTrace();
        }


    }
    public static void clearList(String path) {
        HashMap<Integer, ArrayList<String>> fileWords =  _fileMap.get(path);
        for(int level = 1; level <= AppModel.getNumLevels(); level++){
            ArrayList<String> levelWords =  new ArrayList<>();
            fileWords.put(level, levelWords);
            _fileMap.put(path, fileWords);
        }
    }

    public static void clearFile(String path) {
        File f = new File(path);
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

    /*
     * Clear all data from files
     */
    public static void clearFiles() {
        // Loop through each file and if it exists clear it
        for (WordFile filename : WordFile.values()) {
            // Don't want to clear the spelling list
            // Clear the file then sync it
            clearList(filename + "");
            clearFile(filename + "");
            SyncFile(filename + "");
        }
    }


    /*
     * Get all the words in a level
     */


    /*
     * Helper method that returns all words from a level in a file selected
     */
    public static ArrayList<String> getWordsFromLevel(String path, int level) {
        return getLevelWords(path, level);
    }



    public static void addUniqueWordToLevel(String path, String word, int level) {
        if (!containsWordInLevel(path, word, level)) {
            addWordToLevel(path, word, level);
        }
        SyncFile(path);
    }

    public static void addWordToLevel(String path, String word, int level) {
        getLevelWords(path, level).add(word);
        SyncFile(path);
    }

    /*
     * returns whether a word is in a level
     */
    public static boolean containsWordInLevel(String path, String word, int level) {
        return getLevelWords(path, level).contains(word);
    }


    /*
     * Removes word from list // need to make sure it keeps files in sync
     */
    public static void removeWordFromLevel(String path, String word, int level) {
        if(containsWordInLevel(path, word, level)) {
            getLevelWords(path, level).remove(word);
        }
        // Synce file with array
        SyncFile(path);
    }

    /*
     * returns how many matches for a word there are in a level of a file
     */
    public static int countOccurencesInLevel(String path, String word, int level) {
        ArrayList<String> levelWords = getLevelWords(path, level);
        if(levelWords == null) {
            return 0;
        } else{
                int count = 0;
                for (String s : levelWords) {
                    if (s.equals(word)) {
                        count++;
                    }
                }
                return count;
            }
        }

    public static boolean isEmpty(String path, int level) {
        ArrayList<String> levelWords = getLevelWords(path, level);
        return levelWords.size() == 0;
    }
}