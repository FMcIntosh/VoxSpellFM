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
import jdk.internal.util.xml.impl.Input;

/**
 * Created by Fraser McIntosh on 14/09/2016.
 */
public class EnterWordScene {
    private QuizModel _quizModel;
    private boolean _isReview;
    private final Image CORRECT = new Image(new File(".media/img/tick.png").toURI().toString());
    private final Image INCORRECT = new Image(new File(".media/img/cross.png").toURI().toString());
    private WordState _currentWordState;
    private TextField _input = new TextField();
    public EnterWordScene() {
        _quizModel = AppModel.getQuizModel();
        _isReview = _quizModel.getIsReview();
        _currentWordState = _quizModel.getWordState();
    }

    /*
     * Build the components of the Enter Word app.scene, and return a app.scene object
     */
    private Scene build() {
        HBox resultTab = new HBox(30);
        ArrayList<WordModel> quizWords = _quizModel.getQuizWords();

        // Header bar which has ticks/crosses for correct and
        // incorrect words in the quiz
        int i = 1;
        for(WordModel word : quizWords) {
            VBox result = new VBox(5);
            Label resulLabel = new Label("Word " + i);
            result.getChildren().add(resulLabel);

            // if the word is correct ,show a tick
            if(word.getWordState().equals(WordState.CORRECT)) {
                ImageView img = new ImageView(CORRECT);
                result.getChildren().add(img);
                // if the word is incorrect, show a cross
            } else if (word.getWordState().equals(WordState.INCORRECT)) {
                ImageView img = new ImageView(INCORRECT);
                result.getChildren().add(img);
                // if neither, show nothing
            } else {
                Pane pane = new Pane();
                pane.setPrefSize(80, 80);
                result.getChildren().add(pane);
            }
            result.setAlignment(Pos.CENTER);
            resultTab.getChildren().add(result);
            i++;
        }
        resultTab.setAlignment(Pos.CENTER);
        resultTab.setTranslateY(-200);



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

        //Text _input where user will enter word
        _input= new TextField();
        _input.requestFocus();
        _input.setPromptText("Spell word here");
        /*
         * Button that is responsible for submitting a word. This involves checking
         * whether the word is spelt correctly or not and asking the app.model to
         * update itself based on this result
         */
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
                    // Either display app.scene.WordResultScene or QuizResultScene
                    new EnterWordScene().setScene();
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

                // focus back on input
                _input.requestFocus();
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
//                    FestivalStub.spellWord(_quizModel.getCurrentWord());
                    try {
						Festival.spellWord(_quizModel.getCurrentWord());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    // Focus back on input
                    _input.requestFocus();
                }
            });

            // add this button to the inner layout
            innerLayout.getChildren().addAll(spellButton);
        }

        Button actionButton = new Button();
        /*
         * If quiz is finished take us to the finished quiz app.scene
         */
        if (_quizModel.getQuizState() == QuizState.FINISHED) {
            actionButton.setText("Finish Quiz");
            actionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Update number of levels unlocked
                    if(_quizModel.getLevelSelected().getStarUnlocked()) {
                        new StarEarnedScene().setScene();
                    } else {
                        new QuizFinishedScene().setScene();
                    }
                }
            });
        }

        // Button to return to main menu
        Button returnBtn = new Button("Exit Quiz");
        returnBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                new AlertBox("The data from the quiz so far has been saved").setScene();
                MainMenuScene.setScene();
            }
        });
        returnBtn.setTranslateY(100);
        // add components to inner layout
        innerLayout.getChildren().addAll(sayButton, _input, submitButton);
        innerLayout.setAlignment(Pos.CENTER);

        VBox outerLayout = new VBox(10);
        outerLayout.setPadding(new Insets(30, 0, 0, 0));

        // add the inner components to the outer layout
        if(_quizModel.getQuizState() ==QuizState.FINISHED) {
            outerLayout.getChildren().addAll(resultTab,label1,currentScoreLabel, actionButton);
        } else {
            outerLayout.getChildren().addAll(resultTab, label1, currentScoreLabel, wordCountLabel, innerLayout, returnBtn);
        }
        outerLayout.getStyleClass().add("root");
        outerLayout.setAlignment(Pos.CENTER);

        // create new app.scene using outerLayour
        return new Scene(outerLayout, AppModel.getWidth(), AppModel.getHeight());
    }

    public void setScene() {
        // Say work when scene is created
        if(!_quizModel.getQuizState().equals(QuizState.FINISHED )) {
//            FestivalStub.sayWord("Please spell the word   " + _quizModel.getCurrentWord());
                Festival.sayWordWithIntro(_quizModel.getCurrentWord());
        }
        //Build app.scene
        Scene EnterWordScene = build();
        if(!_isReview) EnterWordScene.getStylesheets().add("app/style/quiz.css");
        else EnterWordScene.getStylesheets().add("app/style/review.css");

        //focus the input ready to enter a word
        _input.requestFocus();
        //Set app.scene in app.AppModel
        AppModel.setScene(EnterWordScene);
    }

}
