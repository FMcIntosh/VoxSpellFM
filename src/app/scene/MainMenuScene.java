package app.scene;

import app.AppModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainMenuScene {
	private final static int BTN_WIDTH=400;
	private final static int BTN_HEIGHT=80;

	private static Scene build(){
		//Sets title and labels for the window
		AppModel.getWindow().setTitle("Main Menu");
		Label title = new Label("Welcome to VoxSpell!");
		title.setFont(Font.font ("Verdana", 35));
		title.setTranslateY(-40);
		
		//Creates new quiz button, that will start a new quiz when clicked
		Button quizBtn = new Button("New Quiz");
		quizBtn.setMinWidth(BTN_WIDTH);
		quizBtn.setMinHeight(BTN_HEIGHT);
		quizBtn.getStyleClass().add("quiz-btn");
		quizBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setIsReview(false);
				LevelSelectScene.setScene();
			}
		});
		
		//Creates review button, that will start a new review quiz when clicked
		Button reviewBtn = new Button("Review");
		reviewBtn.setMinWidth(BTN_WIDTH);
		reviewBtn.setMinHeight(BTN_HEIGHT);
		reviewBtn.getStyleClass().add("review-btn");
		reviewBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				LevelSelectScene.setIsReview(true);
				LevelSelectScene.setScene();
			}
		});

		//Creates review button, that will start a new review quiz when clicked
		Button ttBtn = new Button("Time Trial");
		ttBtn.setMinWidth(BTN_WIDTH);
		ttBtn.setMinHeight(BTN_HEIGHT);
		ttBtn.getStyleClass().add("tt-btn");
		ttBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				TimeTrialUnlockScene.setScene();
			}
		});
		
		//Creates statistics button, that will present word statistics when clicked
		Button statsBtn = new Button("Statistics");
		statsBtn.setMinWidth(BTN_WIDTH);
		statsBtn.setMinHeight(BTN_HEIGHT);
		statsBtn.getStyleClass().add("stats-btn");
		statsBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				StatisticsScene.setScene();
			}
			
		});
		
		//Creates settings button, that will take use to settings menu when clicked
		Button settingsBtn = new Button("Settings");
		settingsBtn.setMinWidth(BTN_WIDTH);
		settingsBtn.setMinHeight(BTN_HEIGHT);
		settingsBtn.getStyleClass().add("settings-btn");
		settingsBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				SettingsScene.setScene();
			}
			
		});
		
		//Creates vertical layout with the four buttons
		VBox layout1 = new VBox(10);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(title, quizBtn, reviewBtn, ttBtn, statsBtn, settingsBtn);

		return(new Scene(layout1, AppModel.getWidth(), AppModel.getHeight()));
		
	}
	
	//Builds scene
	public static void setScene(){
		Scene mainMenuScene = build();
		mainMenuScene.getStylesheets().add("app/style/MainMenu.css");
		AppModel.setScene(mainMenuScene);
	}
}
