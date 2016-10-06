package app.scene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import app.AppModel;
import app.model.*;
import app.process.Festival;
import app.process.FestivalStub;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Created by Fraser McIntosh on 14/09/2016.
 */
public class StarEarnedScene {
    private QuizModel _quizModel;
    private LevelModel _level;
    private boolean _isReview;
    private final Image ONE_STAR = new Image(new File("ONE_STAR.png").toURI().toString());
    private final Image TWO_STARS = new Image(new File("TWO_STARS.png").toURI().toString());
    private final Image THREE_STARS= new Image(new File("THREE_STARS.png").toURI().toString());
    private WordState _currentWordState;
//    private static final Image CORRECT = new Image(EnterWordScene.class.getResourceAsStream("stars0.png"));

    public StarEarnedScene() {
        _quizModel = AppModel.getQuizModel();
        _isReview = _quizModel.getIsReview();
        _currentWordState = _quizModel.getWordState();
        _level = _quizModel.getLevelSelected();
    }

    /*
     * Build the components of the Enter Word app.scene, and return a app.scene object
     */
    private Scene build() {
        VBox root = new VBox(30);
        Label l1 = new Label();
        Label l2 = new Label();
        l2.setFont(Font.font ("Verdana", 30));
        ImageView img;
        switch(_level.getTimesCompleted()) {
            case 1:
                l1.setText("You have earned your first star. Get two more to unlock the time trial.");
                l2.setText("Fantastic!");
                img = new ImageView(ONE_STAR);
                break;
            case 2:
                l1.setText("You have earned your second star. One more until you unlock the time trial.");
                l2.setText("Great job!");
                img = new ImageView(TWO_STARS);
                break;
            case 3:
                l1.setText("Time trial unlocked!");
                l2.setText("Fantastic!");
                img = new ImageView(THREE_STARS);
                break;
            default:
                img = new ImageView(THREE_STARS);
        }

        Button button = new Button("Got it!");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new QuizFinishedScene().setScene();
            }
        });
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(l2, img, l1, button );
        return new Scene(root, AppModel.getWidth(), AppModel.getHeight());

    }

    public void setScene() {
        //Build app.scene
        Scene scene = build();
        scene.getStylesheets().add("app/style/quiz.css");

        //Set app.scene in app.AppModel
        AppModel.setScene(scene);
    }

}
