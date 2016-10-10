package app.model;

import app.AppModel;

import javax.activation.FileDataSource;
import java.io.*;

/**
 * Created by Fraser McIntosh on 9/10/2016.
 *
 * Models a spelling list, based off a settings file
 */
public class SpellingListModel {


    // Allows user to change the spelling list, provided the file
    // is valide
    public static void setSpellingListFile(File file) throws IOException  {
        validateFile(file);
        AppModel.setSpellingListPath(file.getPath());
    }

    // Returns the current path of the spelling list
    public static String getPath() {
        return AppModel.getSpellingListPath();
    }

    // Returns the spelling list as a file
    public static File getFile() {
        return new File(getPath());
    }

    // Checks that the file is valid
    private static void validateFile(File file) throws IOException {
        if(file == null || !file.isFile()) throw new FileNotFoundException();
        FileDataSource ds = new FileDataSource(file);
        String contentType = ds.getContentType();
        if(!contentType.equals("text/plain")) throw new IOException("Incorrect File Type");
        if(!checkFileFormat(file)) throw new IOException("Incorrect Format");
    }

    // helper method to check that the spelling list file is formatted correctly
    private static boolean checkFileFormat(File file) throws FileNotFoundException {
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(file));

            String currentLine = in.readLine();
            if (currentLine.contains("%"))
                return true;
        } catch (IOException e) {

        }
        return false;
    }

    public String toString() {
        return AppModel.getSpellingListPath();
    }
}
