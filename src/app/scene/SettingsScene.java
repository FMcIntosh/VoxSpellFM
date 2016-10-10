package app.scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import app.AppModel;
import app.model.FileModel;
import app.model.LevelModel;
import app.model.SpellingListModel;
import app.model.TimeTrialModel;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;

public class SettingsScene {
	private static Scene build(){
		Label selectVoiceLbl= new Label("Select Voice to read out Quiz Words");


		//Create two radio buttons for switching between app.process.Festival voices
		RadioButton defaultBtn = new RadioButton("Default Voice");
		RadioButton nzBtn = new RadioButton("New Zealand Voice");
		defaultBtn.setTextFill(Color.WHITE);
		nzBtn.setTextFill(Color.WHITE);

		//Create group for the two radio buttons, to make them toggleable
		final ToggleGroup group = new ToggleGroup();
		defaultBtn.setToggleGroup(group);
		nzBtn.setToggleGroup(group);

		//Decide based on the current selected voice which btn to appear selected
		if(AppModel.getVoice().equals("default")){
			defaultBtn.setSelected(true);
		}else{
			nzBtn.setSelected(true);
		}

		//Set voice as default festival voice
		defaultBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				try {
					AppModel.setVoice("default");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		//Set voice as New Zealand festival voice
		nzBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				try {
					AppModel.setVoice("new_zealand");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		//Button to allow user to choose a different word file
		Button fileBtn = new Button("Browse");
		fileBtn.setTranslateY(30);
		//Button resets appModel data, resets word statistics and builds the welcome app.scene again
		fileBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				File file = fileChooser.showOpenDialog(AppModel.getWindow());
				try {
					SpellingListModel.setSpellingListFile(file);
				} catch (FileNotFoundException e) {
					System.out.println("No such file");
				} catch (IOException e) {
					if(e.getMessage().equals("Incorrect Format")) {
						new AlertBox("File is in Incorrect Format. See Help for details").setScene();
					} else if(e.getMessage().equals("Incorrect File Type")) {
						new AlertBox("Incorrect File Type. Please upload a .txt file").setScene();
					}
				}
			}
		});

		Label browseLabel = new Label("Choose a different spelling list file");
		browseLabel.setTranslateY(30);

		//Button to clear all data from application, as if starting from new
		Button resetBtn = new Button("Reset Data");
		resetBtn.setTranslateY(50);
		//Button resets appModel data, resets word statistics and builds the welcome app.scene again
		resetBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				try {
					AppModel.resetApp();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Label resetLabel = new Label("Resets all user data");
		resetLabel.setTranslateY(50);

		//Main menu button to return user to main menu screen
		Button returnBtn = new Button("Return to Main Menu");
		returnBtn.setTranslateY(100);
		returnBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				MainMenuScene.setScene();
			}
		});

		//Sets vertical layout
		VBox layout1 = new VBox(20);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(selectVoiceLbl, defaultBtn, nzBtn, fileBtn, browseLabel, resetBtn, resetLabel, returnBtn);

		return(new Scene(layout1, AppModel.getWidth(), AppModel.getHeight()));

	}
	public static void setScene(){
		Scene settingsScene = build();
		settingsScene.getStylesheets().add("app/style/settings.css");
		AppModel.setScene(settingsScene);
	}
}
