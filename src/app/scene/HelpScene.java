package app.scene;

import app.AppModel;
import app.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class HelpScene {

    private static boolean _isReview = false;
    private final static int BTN_WIDTH=120;
    private final static int BTN_HEIGHT=100;

    private static Scene build(){

        //Labels current quiz mode - either new quiz or review mode


        //Sets title to mode type

        Label titleLbl = new Label("Help");
        titleLbl.setFont(Font.font ("Verdana", 40));
        titleLbl.setTranslateY(-100);

        Label levelLbl = new Label("Levels");
        levelLbl.setFont(Font.font ("Verdana", 20));
        Label lb2 = new Label("To complete a level, you must get 9 or more out of 10 on a level. This will unlock the next level.");

        Label ttLbl = new Label("Time Trials");
        ttLbl.setFont(Font.font ("Verdana", 20));
        Label lb3 = new Label("To earn a star, you must get 10 out of 10 on a level. Earn three starts to unlock the time trial for that level");
        //Details instructions for user

        Label splistLbl = new Label("Spelling List");
        splistLbl.setFont(Font.font ("Verdana", 20));

        Label lb4 = new Label("You can change the spelling list used by VoxSpell in the 'Settings' section. The spelling list must a .txt file in the format:" +
                "\n%Level" +
                "\nWords" +
                "\n%Level2" +
                "\nWords" +
                "\n...etc");
        //Create overarching layout for this app.scene and centers it
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                MainMenuScene.setScene();
            }
        });


        //Adds all components to root layout and returns the app.scene containing the layout
        root.getChildren().addAll(titleLbl, levelLbl, lb2, ttLbl, lb3, splistLbl, lb4, returnBtn);
        return(new Scene(root, AppModel.getWidth(), AppModel.getHeight()));
    }

    //Sets the app.scene of the window as the Level Select Scene
    public static void setScene(){
        Scene scene = build();
        scene.getStylesheets().add("app/style/help.css");
        AppModel.setScene(scene);
    }

    //Allows this class to know if it is in review mode or not
    public static void setIsReview(boolean isReview){
        _isReview = isReview;
    }

}
