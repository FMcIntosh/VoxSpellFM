package app.scene;

import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.activation.FileDataSource;
import java.io.*;
import java.text.ParseException;
import java.io.IOException;

/**
 * Created by Fraser McIntosh on 9/10/2016.
 */
public class FileChooserScene {

    static Stage _window;

    public static void build() {
        //Clear history, maybe could be in a better place in terms of responsibilities
        _window = new Stage();

        //Block user interaction with other windows until this window is
        // dealt with
        _window.initModality(Modality.APPLICATION_MODAL);
        _window.setTitle("No Words");
        _window.setMinWidth(300);
    }

    public void setScene() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
       File file = fileChooser.showOpenDialog(_window);
        try {
            validateFile(file);
        } catch (FileNotFoundException e) {
            System.out.println("No such file");
        } catch (IOException e) {
            if(e.getMessage().equals("Incorrect Format")) {
                System.out.println("Wrong format");
            } else if(e.getMessage().equals("Incorrect File Type")) {
                System.out.println("Incorrect f type");
            }
        }
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
}
