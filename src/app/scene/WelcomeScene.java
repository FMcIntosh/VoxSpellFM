package app.scene;

import java.io.FileNotFoundException;

import app.AppModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WelcomeScene{

	private static Scene build() throws Exception {
		AppModel.getWindow().setTitle("Welcome");

		//Create app.scene labels
		Label welcomeLbl = new Label("Welcome to VoxSpell");
		Label selectLbl = new Label("To get started, please select your desired starting level");

		//create drop down box containing all available levels
		final ComboBox<String> comboBox = new ComboBox<String>();
		for (int i = 1; i<= AppModel.getNumLevels(); i++){
			comboBox.getItems().add("Level "+i);
		}

		
		//Is modifiable to fit the window appropriately
		comboBox.setVisibleRowCount(4);

		//Create button to select the level
		Button selectBtn = new Button("Select");

		//when selectbutton is clicked, read selected value from drop down box
		selectBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				if(comboBox.getValue() == null){
					try {
						setLevelNo(1 + "");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					try {
						setLevelNo(comboBox.getValue());
						AppModel.setNotFirstTime();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			LevelUnlockScene.setScene();
			}
		});
		
		//Set vertical layout with all components aligned to center
		VBox layout1 = new VBox(20);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(welcomeLbl, selectLbl, comboBox, selectBtn);

		return(new Scene(layout1, AppModel.getWidth(), AppModel.getHeight()));
	}
	
	public static void setScene() throws Exception{
		Scene welcomeScene = build();
		AppModel.setScene(welcomeScene);
	}

	//Helper method to obtain int value corresponding to level
	private static void setLevelNo(String levelString) throws FileNotFoundException{
		String str = levelString.replaceAll("\\D+","");
		int selectedLevel = Integer.parseInt(str);
		
		//Update app.AppModel with new level-unlocked value
		AppModel.setLevelsUnlocked(selectedLevel);
	}


}
