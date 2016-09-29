package app.scene;

import java.io.IOException;

import app.AppModel;
import app.model.QuizModel;
import app.process.Festival;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Created by Fraser McIntosh on 14/09/2016.
 */
public class EnterWordScene {
    private QuizModel _quizModel;
    private boolean _isReview;

    public EnterWordScene() {
        _quizModel = AppModel.getQuizModel();
        _isReview = _quizModel.getIsReview();
    }

    /*
     * Build the components of the Enter Word app.scene, and return a app.scene object
     */
    private Scene build() {

        Label label1 = new Label("New Spelling Quiz");
        if(_isReview) {
            label1.setText("Review Quiz");
        }
        label1.setTranslateY(-100);
        label1.setFont(Font.font ("Verdana", 30));


        Label currentScoreLabel = new Label("Current score: " + _quizModel.getNumCorrectWords() + " out of "+ _quizModel.getCurruntWordIndex());
        currentScoreLabel.setTranslateY(-50);
        currentScoreLabel.setFont(Font.font ("Verdana", 20));
        // Label that displays what number word it is, eg Word 5 of 10
        Label wordCountLabel = new Label("Enter Word " + (_quizModel.getCurruntWordIndex() + 1) + " of " + _quizModel.getNumWordsInQuiz());

        //Text input where user will enter word
        final TextField input = new TextField();
        input.setPromptText("Spell word here");
        /*
         * Button that is responsible for submitting a word. This involves checking
         * whether the word is spelt correctly or not and asking the app.model to
         * update itself based on this result
         */
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO
                // submit answer which  returns false if word is invalid
                boolean validWord = _quizModel.submitAnswer(input.getText());
                // Build appropriate app.scene depending on app.model state
                if (!validWord) {
                    // Would like it to be a pop up, so might need a new method for this in app.AppModel
                    InvalidInputScene.setScene();
                } else {
                    // Either display app.scene.WordResultScene or QuizResultScene
                    new WordResultScene().setScene();
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
                try {
					Festival.sayWord(_quizModel.getCurrentWord());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        //Layout
        HBox innerLayout = new HBox();
        if (_isReview) {
        /*
         * Button that causes festival to spell out the current word
         */
            Button spellButton = new Button("Spell Out Word");
            spellButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //TODO
                    //Spell out word
                    try {
						Festival.spellWord(_quizModel.getCurrentWord());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            });

            // add this button to the inner layout
            innerLayout.getChildren().addAll(spellButton);
        }

        // add components to inner layout
        innerLayout.getChildren().addAll(sayButton, input, submitButton);

        innerLayout.setAlignment(Pos.CENTER);
        VBox outerLayout = new VBox(10);
        outerLayout.setPadding(new Insets(30, 0, 0, 0));

        // add the inner componenets to the outer layout
        outerLayout.getChildren().addAll(label1,currentScoreLabel, wordCountLabel, innerLayout);
        outerLayout.setAlignment(Pos.CENTER);

        // create new app.scene using outerLayour
        return new Scene(outerLayout, AppModel.getWidth(), AppModel.getHeight());
    }

    public void setScene() {
        //Build app.scene
        Scene EnterWordScene = build();

        //Set app.scene in app.AppModel
        AppModel.setScene(EnterWordScene);
    }

}
