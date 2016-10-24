package app.scene;

import app.AppModel;
import app.model.QuizModel;
import app.model.QuizState;
import app.process.Festival;
import app.process.FestivalStub;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by Fraser McIntosh on 14/09/2016.
 */
public class TimeTrialScene {
    private static final Integer STARTTIME = 60;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private Integer timeSeconds = STARTTIME;
    private boolean _finished = false;
    private TextField _input = new TextField();
    private QuizModel _quizModel = AppModel.getQuizModel();
//    private static final Image CORRECT = new Image(EnterWordScene.class.getResourceAsStream("stars0.png"));

    public TimeTrialScene() {

    }

    /*
     * Build the components of the Enter Word app.scene, and return a app.scene object
     */
    private Scene build() {
        // Setup the Stage and the Scene (the scene graph)

        // Add robustness in case the time trial runs out of words
        if(_quizModel.getQuizState().equals(QuizState.FINISHED)) {
            new FinishTimeTrialScene().setScene();
        }
        // Configure the Label
        timerLabel.setText(timeSeconds.toString());
        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 4em;");
        timerLabel.setTranslateY(-200);
        // Create and configure the Button
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
                                                new FinishTimeTrialScene().setScene();
                                            }
                                        }
                                    }));
                    timeline.playFromStart();
//                }
//            });
        //Text _input where user will enter word
        _input = new TextField();
        _input.setPromptText("Spell word here");
        /*
         * Button that is responsible for submitting a word. This involves checking
         * whether the word is spelt correctly or not and asking the app.model to
         * update itself based on this result
         */

        Label lb = new Label("Score: " + _quizModel.getNumCorrectWords());
        Button submitButton = new Button("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO
                // submit answer which  returns false if word is invalid
                boolean validWord = _quizModel.submitAnswer(_input.getText());
                // Build appropriate app.scene depending on app.model state
                if (!validWord) {
                    // Would like it to be a pop up, so might need a new method for this in app.AppModel
                    new AlertBox("Please enter valid _input. Alphabetical characters only").setScene();
                } else {
                    lb.setText("Score: " + _quizModel.getNumCorrectWords());
                    _input.clear();
                    // say the next word.
                    if(!_quizModel.getQuizState().equals(QuizState.FINISHED )) {
//            FestivalStub.sayWord("Please spell the word   " + _quizModel.getCurrentWord());
                        Festival.sayWord(_quizModel.getCurrentWord());
                    }
                }
            }
        });

        /*
         * Button that causes festival to say the current word
         */
        Button sayButton = new Button("Say Word");
        // Change this for review
        sayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO
                //Say word
//                FestivalStub.sayWord(_quizModel.getCurrentWord());
					Festival.sayWord(_quizModel.getCurrentWord());
            }
        });

        //Score Label

        // Button that quits timetrial
        Button returnBtn = new Button("Exit Quiz");
        returnBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                timeline.stop();
                _finished = true;
                new AlertBox("Warning: You are about to exit the time trial").setScene();
                MainMenuScene.setScene();
            }
        });
        returnBtn.setTranslateY(100);

        //Layout
        HBox innerLayout = new HBox();
        // add components to inner layout
        innerLayout.getChildren().addAll(sayButton, _input, submitButton);

        innerLayout.setAlignment(Pos.CENTER);
            // add this button to the inner layout
        // Create and configure VBox
        // gap between components is 20
        VBox vb = new VBox(20);
        // center the components within VBox
        vb.setAlignment(Pos.CENTER);
        // Make it as wide as the application frame (scene);

        // Add the button and timerLabel to the VBox
        vb.getChildren().addAll(timerLabel, innerLayout, lb, returnBtn);
        // Add the VBox to the root component
//        root.getChildren().add(vb);
        return new Scene(vb, AppModel.getWidth(), AppModel.getHeight());
    }

    public void setScene() {
        // Say work when scene is created
        if(!_quizModel.getQuizState().equals(QuizState.FINISHED )) {
//            FestivalStub.sayWord("Please spell the word   " + _quizModel.getCurrentWord());
                Festival.sayWord( _quizModel.getCurrentWord());
        }
        //Build app.scene
        Scene scene = build();

        //input focus automatically
        _input.requestFocus();
         scene.getStylesheets().add("app/style/timetrial.css");
        //Set app.scene in app.AppModel
        AppModel.setScene(scene);
    }

}
