package app.scene;

import app.AppModel;
import app.model.LevelModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;


public class LevelUnlockScene {
	private static Scene build(){
		Label confirmLbl = new Label();

		//If only level 1 is selected, grammar is unique so window title and label wording is set accordingly
		if(AppModel.getLevelsUnlocked() == 1){
			confirmLbl.setText("You have unlocked " + LevelModel.get(0));
		}else{//If multiple levels are unlocked, window title and labels are set accordingly

			confirmLbl.setText("You have unlocked " + LevelModel.get(AppModel.getLevelsUnlocked()-1)
			+".\nYou may now access " + LevelModel.get(0) +  " to " + LevelModel.get(AppModel.getLevelsUnlocked()-1) + ".");
			confirmLbl.setTextAlignment(TextAlignment.CENTER);
		}
		Button okBtn = new Button("Okay");
		//Sets action event to build and set new app.scene as the main menu app.scene, once okay button is clicked
		okBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				MainMenuScene.setScene();
			}
		});
		
		//Sets vertical layout
		VBox layout1 = new VBox(20);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(confirmLbl, okBtn);

		return(new Scene(layout1, AppModel.getWidth(), AppModel.getHeight()));
	}
	
	public static void setScene(){
		Scene levelUnlockScene = build();
		levelUnlockScene.getStylesheets().add("app/style/main.css");
		AppModel.setScene(levelUnlockScene);
	}
}
