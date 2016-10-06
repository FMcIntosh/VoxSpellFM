package app.scene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import app.AppModel;
import app.model.QuizModel;
import app.model.QuizState;
import app.model.WordModel;
import app.model.WordState;
import app.process.Festival;
import app.process.FestivalStub;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Created by Fraser McIntosh on 14/09/2016.
 */
public class TimeTrialScene {
    private static final Integer STARTTIME = 60;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private Integer timeSeconds = STARTTIME;
    private boolean _finished = false;
//    private static final Image CORRECT = new Image(EnterWordScene.class.getResourceAsStream("stars0.png"));

    public TimeTrialScene() {

    }

    /*
     * Build the components of the Enter Word app.scene, and return a app.scene object
     */
    private Scene build() {
        // Setup the Stage and the Scene (the scene graph)

        // Configure the Label
        timerLabel.setText(timeSeconds.toString());
        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 4em;");

        // Create and configure the Button
        Button button = new Button();
        button.setText("Start Timer");
//        button.setOnAction(new EventHandler<ActionEvent>() {
//                public void handle(ActionEvent event) {
                    if (timeline != null) {
                        timeline.stop();
                    }
                    timeSeconds = STARTTIME;

                    // update timerLabel
                    timerLabel.setText(timeSeconds.toString());
                    timeline = new Timeline();
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.getKeyFrames().add(
                            new KeyFrame(Duration.seconds(1),
                                    new EventHandler<ActionEvent>() {
                                        // KeyFrame event handler
                                        public void handle(ActionEvent event) {
                                            timeSeconds--;
                                            // update timerLabel
                                            timerLabel.setText(
                                                    timeSeconds.toString());
                                            if (timeSeconds <= 0) {
                                                timeline.stop();
                                                _finished = true;
                                            }
                                        }
                                    }));
                    timeline.playFromStart();
//                }
//            });

        // Create and configure VBox
        // gap between components is 20
        VBox vb = new VBox(20);
        // center the components within VBox
        vb.setAlignment(Pos.CENTER);
        // Make it as wide as the application frame (scene);

        // Add the button and timerLabel to the VBox
        vb.getChildren().addAll(timerLabel, button);
        // Add the VBox to the root component
//        root.getChildren().add(vb);
        return new Scene(vb, AppModel.getWidth(), AppModel.getHeight());
    }

    public void setScene() {
        //Build app.scene
        Scene EnterWordScene = build();
//        EnterWordScene.getStylesheets().add("app/style/review.css");
        //Set app.scene in app.AppModel
        AppModel.setScene(EnterWordScene);
    }

}
