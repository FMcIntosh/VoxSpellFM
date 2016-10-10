package app.scene;
import app.AppModel;
import app.model.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StatisticsScene {
	private static Scene build(){
		
		//Create root and app.scene to be built
		VBox root = new VBox(10);
		root.setAlignment(Pos.CENTER);
		
        Scene scene = new Scene(root, AppModel.getWidth(), AppModel.getHeight(), Color.WHITE);

        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: #232323");
        BorderPane borderPane = new BorderPane();
        
        //Create table layout for each level, in each of their own separate tabs
        for (int i = 1; i <= AppModel.getNumLevels(); i++) {
           LevelModel level =  LevelModel.get(i-1);
        	//Create tab for level based on the current iteration number of for loop
            Tab tab = new Tab();
            tab.setText(level + "");
            int correct = FileModel.getWordsFromLevel(WordFile.MASTERED.getPath(), level.getLevelAsInt()).size();
            int incorrect = FileModel.getWordsFromLevel(WordFile.FAILED.getPath(), level.getLevelAsInt()).size();
            double percentage = ((correct + incorrect) != 0) ? ((correct * 100) / (correct + incorrect)) : -1;
            Label lb = new Label(percentage+ "%");
            lb.setFont(Font.font ("Verdana", 30));

            Label lb2 = new Label("Percentage Correct:");
            Label timeTrial = new Label();
            int timesCompletedLevel =level.getTimesCompleted();
            if(timesCompletedLevel < 3) {
                timeTrial.setText("Get 100% on a level 3 times to unlock the time trial");
            } else {
                timeTrial.setText("Time Trial High Score: " +TimeTrialModel.getHighScoreAtLevel(level));
            }

            lb.setTranslateY(-80);
            lb2.setTranslateY(-80);
            timeTrial.setTranslateY(-30);
            //Create new instance of app.model.Statistics class passing level number into the object
            Statistics statsObject = new Statistics(i);
            VBox inner = new VBox();
            inner.setAlignment(Pos.CENTER);
            if(percentage != -1) {
                inner.getChildren().addAll(lb2, lb, timeTrial, statsObject.constructTableLayout());
            } else {
                Label noWords = new Label("No Words Attempted");
                noWords.setFont(Font.font ("Verdana", 30));
                inner.getChildren().addAll(noWords);
            }
            //Construct the table of words for current level and add to this level's tab pane
            tab.setContent(inner);
            tabPane.getTabs().add(tab);
        }
        
        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.setStyle("-fx-background-color: #232323");
        borderPane.setCenter(tabPane);
        
        //Create button to return user to main menu when finished looking at stats
        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				MainMenuScene.setScene();
			}
        });
        returnBtn.setTranslateY(-30);
        root.getChildren().addAll(borderPane, returnBtn);
        return scene;
	}
	
	public static void setScene(){
		Scene scene = build();
        scene.getStylesheets().add("app/style/stats.css");
        //Set app.scene in app.AppModel
        AppModel.setScene(scene);
		AppModel.setScene(scene);
	}
}
