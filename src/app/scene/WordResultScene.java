package app.scene;

import java.io.IOException;

import app.AppModel;
import app.model.QuizModel;
import app.model.QuizState;
import app.model.WordState;
import app.process.Festival;
import app.process.FestivalStub;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Created by Fraser McIntosh on 14/09/2016.
 */
public class WordResultScene {
    private QuizModel _quizModel;
    @SuppressWarnings("unused")
	private boolean _isReview;
    private WordState _currentWordState;

    WordResultScene() {
        _quizModel = AppModel.getQuizModel();
        _isReview = _quizModel.getIsReview();
        _currentWordState = _quizModel.getWordState();
    }

    // Only get to this app.scene if quiz still going, so don't need to check that (or do we??)
    private Scene build() {

        //Label informing the user if the answered correctly or not
        Label label1 = new Label();
        if (_currentWordState.equals(WordState.CORRECT)) {
            label1.setText("Correct");
            FestivalStub.sayWord("Correct");
//            try {
//				Festival.sayWord("Correct");
//			} catch (IOException | InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        } else {
            label1.setText("Incorrect");
            FestivalStub.sayWord("Incorrect");
//            try {
//				Festival.sayWord("Incorrect");
//			} catch (IOException | InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        }

        // Button that either says "Next Word", or "Try Again", depending
        // on whether the previous answer was correct or not
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
                     new QuizFinishedScene().setScene();
                }
            });

        } else {
            actionButton.setText("Next Word");

             // Either way, this button will take the user back to the 'Enter Word' Scene
            actionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Refresh word state if word is finished (if word was incorrect don't referesh
                    // as need to use it again
                    if(!_currentWordState.equals(WordState.INCORRECT) ){
                        _quizModel.nextWord();
                    }
                    new EnterWordScene().setScene();
                }
            });

        }

        //Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, actionButton);
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
