package app.scene;

import app.model.SpellingListModel;
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
            SpellingListModel.setSpellingListFile(file);
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


}
