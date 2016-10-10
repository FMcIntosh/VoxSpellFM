package app.scene;

import app.AppModel;
import app.model.LevelModel;
import app.model.QuizModel;
import app.model.WordState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Created by Fraser McIntosh on 19/09/2016.
 *
 * Class that is shown at the end of a quiz
 */
public class QuizFinishedScene {
    private QuizModel _quizModel;
    private boolean _isReview;
    @SuppressWarnings("unused")
	private WordState _currentWordState;

    QuizFinishedScene() {
        _quizModel = AppModel.getQuizModel();
        _isReview = _quizModel.getIsReview();
        _currentWordState = _quizModel.getWordState();
    }


    private Scene build() {

        //Label informing the user of the outcome of the quiz
        Label outcomeLabel = new Label();
        outcomeLabel.setFont(Font.font ("Verdana", 30));
        if(_quizModel.getSuccessfulQuiz()) {
            outcomeLabel.setText("Well Done!"); //if successful
        } else if (_quizModel.getIsReview()){
            outcomeLabel.setText("Finished"); // if review
        } else {
        	outcomeLabel.setText("Hard Luck!"); // if unsuccessful
        }

        //Video button ---------------------------------------------------------------
        Button playVideoButton = new Button("Play Reward Video");
        playVideoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MediaPlayerScene.setScene();
//                new VLCJMediaPlayerScene().play();
            }
        });

        playVideoButton.setTranslateY(50);
        // Let user know their score
        Label scoreLabel = new Label("You got " + _quizModel.getNumCorrectWords() +" out of " + _quizModel.getNumWordsInQuiz());

        //Level select button ---------------------------------------------------------------
        // Button that either says "Next Word", or "Try Again", depending
        // on whether the previous answer was correct or not
        Button levelSelectButton = new Button("Level Select");
        levelSelectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LevelSelectScene.setScene();
            }
        });

        //Retry level button ---------------------------------------------------------------
        Button retryLevelButton = new Button("Retry Level");
        retryLevelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AppModel.startQuiz(_isReview, _quizModel.getLevelSelected());
            }
        });

        //Next level button ---------------------------------------------------------------
        Button nextLevelButton = new Button("Next Level");
        nextLevelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AppModel.startQuiz(_isReview, LevelModel.getLevels().get(_quizModel.getLevelSelected().index() + 1));
            }
        });

        //Main Menu button ---------------------------------------------------------------
        // Goes in outer layout
        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                MainMenuScene.setScene();
            }
        });
        returnBtn.setTranslateY(100);

        //Layout to hold the level select, retry and next level buttons
        HBox innerLayout = new HBox(10);
        innerLayout.setAlignment(Pos.CENTER);

        // add components to inner layout
        if(_isReview) {
            //don't add any level buttons

            //If final level, or didn't pass we don't want a next level button
        } else if(_quizModel.getLevelSelected().getLevelAsInt() == AppModel.getNumLevels() || !_quizModel.getSuccessfulQuiz()) {
            innerLayout.getChildren().addAll(levelSelectButton, retryLevelButton);
        } else {
            innerLayout.getChildren().addAll(levelSelectButton, retryLevelButton, nextLevelButton);
        }

        //Layout to hold the outcome and score labels
        VBox layout = new VBox(10);
        layout.getChildren().addAll(outcomeLabel, scoreLabel);

       // Main layout
        VBox outerLayout = new VBox(10);
        outerLayout.setPadding(new Insets(30, 0, 0, 0));



        // If they just unlocked a level in the quiz
        if(_quizModel.getSuccessfulQuiz() && !_isReview) {
        	// If unlocked level is the hardest, then they have unlocked the next hardest
        	if(_quizModel.getIsHardestLevel()) {
        		Label levelUnlockedLabel = new Label();
        		levelUnlockedLabel.setText("You have unlocked "+ LevelModel.get(AppModel.getLevelsUnlocked()-1));
        		//If highest level change text
        	    if (_quizModel.getLevelSelected().index() == AppModel.getNumLevels()){
        	    	levelUnlockedLabel.setText("All levels unlocked!");
        	    }
        	    
        	    layout.getChildren().addAll(levelUnlockedLabel);
        	}

            // Give option to play video
            layout.getChildren().addAll(playVideoButton);
        }
        innerLayout.setTranslateY(80);
        layout.getChildren().addAll(innerLayout, returnBtn);
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, AppModel.getWidth(), AppModel.getHeight());
    }

    public void setScene() {
        Scene WordResultScene = build();
        if(!_isReview) WordResultScene.getStylesheets().add("app/style/quiz.css");
        else WordResultScene.getStylesheets().add("app/style/review.css");
        AppModel.setScene(WordResultScene);
    }


}
