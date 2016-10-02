package app.scene;

import java.io.FileNotFoundException;

import app.AppModel;
import app.model.FileModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class SettingsScene {
	private static Scene build(){
		AppModel.getWindow().setTitle("Settings");

		Label selectVoiceLbl= new Label("Select Voice to read out Quiz Words");


		//Create two radio buttons for switching between app.process.Festival voices
		RadioButton defaultBtn = new RadioButton("Default Voice");
		RadioButton nzBtn = new RadioButton("New Zealand Voice");

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

		//Button to clear all data from application, as if starting from new
		Button resetBtn = new Button("Reset Data");

		//Button resets appModel data, resets word statistics and builds the welcome app.scene again
		resetBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				try {
					AppModel.setToDefault();

					/*
					 * TODO clear history of words and statistics
					 */
					FileModel.clearFiles();

					WelcomeScene.setScene();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		//Main menu button to return user to main menu screen
		Button returnBtn = new Button("Return to Main Menu");
		returnBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				MainMenuScene.setScene();
			}
		});

		//Sets vertical layout
		VBox layout1 = new VBox(20);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(selectVoiceLbl, defaultBtn, nzBtn, resetBtn, returnBtn);

		return(new Scene(layout1, AppModel.getWidth(), AppModel.getHeight()));

	}
	public static void setScene(){
		Scene settingsScene = build();
		AppModel.setScene(settingsScene);
	}
}
