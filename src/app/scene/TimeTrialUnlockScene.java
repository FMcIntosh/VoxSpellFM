package app.scene;

import app.AppModel;
import app.model.LevelModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class TimeTrialUnlockScene {


    private final static int BTN_WIDTH=120;
    private final static int BTN_HEIGHT=100;

    private static Scene build(){

        //Labels current quiz mode - either new quiz or review mode
        Label titleLbl = new Label("Time Trial!");

        //Sets title to mode type
        titleLbl.setFont(Font.font ("Verdana", 30));

        //Details instructions for user
        Label promptLbl = new Label("Please select a level");

        //Create overarching layout for this app.scene and centers it
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);

        //Layout for the 11 buttons
        GridPane buttonLayout = new GridPane();
        buttonLayout.setPadding(new Insets(10,10,10,10));
        buttonLayout.setVgap(20);
        buttonLayout.setHgap(20);

        // j is used for laying out the grid pane
        int j = 0;
        ArrayList<LevelModel> levels = LevelModel.getLevels();
        //Generates a level button for each level, one by one
        for(int i = 1; i <= AppModel.getNumLevels(); i++){
            //Sets the text of button
            LevelModel level = levels.get(i - 1);
            final Button levelBtn = new Button(level+ "");
            levelBtn.setMinWidth(BTN_WIDTH);
            levelBtn.setMinHeight(BTN_HEIGHT);

            //Generates event for the current button
            levelBtn.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    //Gets the level that the button corresponds to
//					String str = levelBtn.getText().replaceAll("\\D+","");
//					int level = Integer.parseInt(str);
                    AppModel.startTimeTrial(level);
                }
            });

            //Disables button if it corresponds to a level that is not unlocked yet
            if(i  > AppModel.getLevelsUnlocked()){
                levelBtn.setDisable(true);
                // Disable review button if there aren't any words to be reviewed
            } else if (level.getTimesCompleted()!= 3){
                levelBtn.setDisable(true);
            }
            //Adds button to the button layout
            GridPane.setConstraints(levelBtn, ((i - 1)%3), j);
            buttonLayout.getChildren().add(levelBtn);
            j = (i) /3;
        }
        //Centers button layout
        buttonLayout.setAlignment(Pos.CENTER);

        //Create button to return user to main menu
        Button returnBtn = new Button("Return to Main Menu");
        returnBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                MainMenuScene.setScene();
            }
        });

        //Adds all components to root layout and returns the app.scene containing the layout
        root.getChildren().addAll(titleLbl, promptLbl,buttonLayout, returnBtn);
        return(new Scene(root, AppModel.getWidth(), AppModel.getHeight()));
    }

    //Sets the app.scene of the window as the Level Select Scene
    public static void setScene(){
        Scene lvlSelectScene = build();
        lvlSelectScene.getStylesheets().add("app/style/timetrial.css");

        AppModel.setScene(lvlSelectScene);
    }


}
