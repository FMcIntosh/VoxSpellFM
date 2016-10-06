package app.scene;
import app.AppModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class StartTimeTrialScene {
    private static Scene build(){
        AppModel.getWindow().setTitle("Time Trial");

        Label lb= new Label("How many points can you get in 60 seconds?");


        //Create two radio buttons for switching between app.process.Festival voices
        Button btn = new Button("Start Time Trial");

        //Set voice as default festival voice
        btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                new TimeTrialScene().setScene();
            }
        });


        //Sets vertical layout
        VBox layout1 = new VBox(20);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(lb, btn);

        return(new Scene(layout1, AppModel.getWidth(), AppModel.getHeight()));

    }
    public static void setScene(){
        Scene settingsScene = build();
        settingsScene.getStylesheets().add("app/style/settings.css");
        AppModel.setScene(settingsScene);
    }
}