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

public class FinishTimeTrialScene {

    private static boolean _isReview = false;
    private final static int BTN_WIDTH=120;
    private final static int BTN_HEIGHT=100;
    private QuizModel _quizModel;
    private int _score;
    private int _previousHighScore;
    private  LevelModel _level;
    FinishTimeTrialScene() {
        _quizModel = AppModel.getQuizModel();
        _score = _quizModel.getNumCorrectWords();
        _level = _quizModel.getLevelSelected();
        _previousHighScore  = TimeTrialModel.getHighScoreAtLevel(_level);
    }

    private Scene build(){
        //Set title
        AppModel.getWindow().setTitle("Level Select");

        //Labels current quiz mode - either new quiz or review mode


        //Sets title to mode type

        Label titleLbl = new Label();
        titleLbl.setFont(Font.font ("Verdana", 30));
        Label lb2 = new Label();
        //Details instructions for user
        if(TimeTrialModel.updateHighScore(_score, _level)) {
              titleLbl.setText("New High Score!");
              lb2.setText("Previous high score: "+ _previousHighScore);
        } else {
            titleLbl.setText("Finished!");
            lb2.setText("Current High Score: "+ _previousHighScore);
        }



        Label l2 = new Label("You got:");
        Label score = new Label(_score + "");
        score.setFont(Font.font ("Verdana", 40));
        //Create overarching layout for this app.scene and centers it
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);

        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                MainMenuScene.setScene();
            }
        });


        //Adds all components to root layout and returns the app.scene containing the layout
        root.getChildren().addAll(titleLbl, l2, score, lb2, returnBtn);
        return(new Scene(root, AppModel.getWidth(), AppModel.getHeight()));
    }

    //Sets the app.scene of the window as the Level Select Scene
    public void setScene(){
        Scene scene = build();
        scene.getStylesheets().add("app/style/timetrial.css");
        AppModel.setScene(scene);
    }

    //Allows this class to know if it is in review mode or not
    public static void setIsReview(boolean isReview){
        _isReview = isReview;
    }

}
