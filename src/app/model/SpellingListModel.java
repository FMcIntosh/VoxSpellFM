package app.model;

import app.AppModel;

import javax.activation.FileDataSource;
import java.io.*;

/**
 * Created by Fraser McIntosh on 9/10/2016.
 */
public class SpellingListModel {



    public static void setSpellingListFile(File file) throws FileNotFoundException, IOException  {
        validateFile(file);
        AppModel.setSpellingListPath(file.getAbsolutePath());
    }

    public static String getPath() {
        return AppModel.getSpellingListPath();
    }

    public static File getFile() {
        return new File(getPath());
    }

    private static void validateFile(File file) throws FileNotFoundException, IOException {
        if(file == null || !file.isFile()) throw new FileNotFoundException();
        FileDataSource ds = new FileDataSource(file);
        String contentType = ds.getContentType();
        if(!contentType.equals("text/plain")) throw new IOException("Incorrect File Type");
        if(!checkFileFormat(file)) throw new IOException("Incorrect Format");
    }

    private static boolean checkFileFormat(File file) throws FileNotFoundException {
        BufferedReader in;
        int level = 0;
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
