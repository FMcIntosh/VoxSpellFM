package app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import app.model.*;
import app.scene.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AppModel extends Application{

	private static Boolean _isFirstTime;
	private static int _levelsUnlocked;
	private static String _voice;
	private static Stage _window;
	private static QuizModel _quizModel;
	private static int _currentSreak;
	private static String _spellingListPath;
	private final static int DEFAULT_WIDTH = 1200;
	private final static int DEFAULT_HEIGHT = 800;
	private static int _numLevels = 11;

	/*
	 * Reads in the 3 settings values from .settings.txt file. 
	 * These values will persist even if the application is closed.
	 */
	private static void setup(){
		//Initialise files
		try{
			BufferedReader reader = new BufferedReader(new FileReader(".app_files/.settings.txt"));
			_isFirstTime = Boolean.parseBoolean(reader.readLine());
			_levelsUnlocked = Integer.parseInt(reader.readLine());
			_voice = reader.readLine();
			_currentSreak = Integer.parseInt(reader.readLine());
			_spellingListPath= reader.readLine();
			reader.close();
		}catch(FileNotFoundException e){
			//worth creating an alert box to inform user that .setting.txt file is missing?
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		LevelModel.initialise();
		FileModel.initialise();
		TimeTrialModel.initialise();
		setNumLevels(FileModel.calcNumLevels());
	}

	//Getter methods
	public static int getLevelsUnlocked(){
		return _levelsUnlocked;
	}
	public static String getVoice(){
		return _voice;
	}
	public static QuizModel getQuizModel(){
		return _quizModel;
	}
	public static Stage getWindow(){
		return _window;
	}
	public static int getWidth(){
		return DEFAULT_WIDTH;
	}
	public static int getHeight(){
		return DEFAULT_HEIGHT;
	}
	public static int getNumLevels(){
		return _numLevels;
	}
	public static String getSpellingListPath(){
		return _spellingListPath;
	}

	//Setter methods
	public static void setLevelsUnlocked(int value) throws FileNotFoundException{
		_levelsUnlocked = value;
		updateTxtFile();
	}
	public static void setNotFirstTime() throws FileNotFoundException{
		_isFirstTime = false;
		updateTxtFile();
	}
	public static void setVoice(String voice) throws FileNotFoundException{
		_voice = voice;
		updateTxtFile();
	}
	public static QuizState setQuizModel(boolean isReview, LevelModel levelSelected){
		_quizModel = new QuizModel(isReview, levelSelected);
		return _quizModel.start();
	}

	public static QuizState setTimeTrialModel( LevelModel levelSelected) {
		_quizModel = new TimeTrialModel(levelSelected);
		return _quizModel.start();
	}

	public static void setNumLevels(int numLevels) {
		_numLevels = numLevels;
	}

	public static void setSpellingListPath(String path) {
		_spellingListPath = path;
		try {
			resetApp();
		} catch (Exception e){}
	}
	//to be invoked from start() method that starts the GUI
	public static void setWindow(Stage window){
		_window = window;
	}
	public static void setScene(Scene scene){
		scene.getStylesheets().add("app/style/styles.css");
		_window.setScene(scene);
		_window.show();
	}

	// reset app to default state
	public static void resetApp() throws Exception {
		setToDefault();
		LevelModel.reset();
		TimeTrialModel.reset();
		FileModel.reset();
		setNumLevels(FileModel.calcNumLevels());
		WelcomeScene.setScene();

	}
	// set the default settings
	public static void setToDefault() throws FileNotFoundException{
		_isFirstTime = true;
		_levelsUnlocked = 0;
		_voice = "default";
		_currentSreak = 0;
		updateTxtFile();
	}

	public static void increaseCurrentSreak() {
		_currentSreak++;
	}

	//Overwrites .settings.txt file with updated field values 
	public static void updateTxtFile() throws FileNotFoundException{
		PrintWriter writer = new PrintWriter(".app_files/.settings.txt");
		writer.println(_isFirstTime.toString());
		writer.println(_levelsUnlocked);
		writer.println(_voice);
		writer.println(_currentSreak);
		writer.println(_spellingListPath);
		writer.close();
	}

	// Starts the main application
	public void start(Stage primaryStage) throws Exception{
		_window = primaryStage;
		_window.setTitle("VoxSpell");
		if(_isFirstTime){
			WelcomeScene.setScene();
		}else{
			MainMenuScene.setScene();
		}
	}


	// Start a quiz at a certain level
	public static void startQuiz( boolean isReview, LevelModel level) {
		//Initialises new quiz app.model object with the selected level
		QuizState quizState = AppModel.setQuizModel(isReview,level);

		//If Quiz is ready
		// Initialises new app.scene.EnterWordScene app.scene to be built next
		if(quizState.equals(QuizState.READY)) {
			EnterWordScene wordScene = new EnterWordScene();
			wordScene.setScene();
			// Else if no words display no words app.scene
		} else if (quizState.equals(QuizState.NO_WORDS)){
			new AlertBox("There are no words to test in this level").setScene();
		}
	}

	// Start a time trial for a specific level
	public static void startTimeTrial(LevelModel level) {
		QuizState quizState = AppModel.setTimeTrialModel(level);
		if(quizState.equals(QuizState.READY)) {
			StartTimeTrialScene scene = new StartTimeTrialScene();
			scene.setScene();
			// Else if no words display no words app.scene
		} else if (quizState.equals(QuizState.NO_WORDS)){
			new AlertBox("There are no words to test in this level").setScene();
		}
	}
	
	// MAIN
	public static void main(String[] args){
		setup();
		launch(args);
	}

}
