package app.process;

import java.io.IOException;
import java.io.PrintWriter;

import app.AppModel;

public class Festival {
	
	public static void sayWordWithIntro(String word) {
		sayWord("Please spell the word " + word);
	}
	//Says aloud the given word
	public static void sayWord(String word) {
		try {
			createTempScript(word);

			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", "festival -b .app_files/.word.scm");
			builder.start();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	//Says aloud the given word, letter by letter
	public static void spellWord(String word) throws IOException, InterruptedException{

			word = word.replaceAll(".(?!$)", "$0 ");
			createTempScript(word);
			
			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", "festival -b .app_files/.word.scm");
			builder.start();
	}
	
	//Write temporary script with bash festival commands, to speak aloud the given text
	private static void createTempScript(String word) throws IOException {
		PrintWriter printWriter = new PrintWriter(".app_files/.word.scm", "UTF-8");
		
		//Sets voice as the one selected in settings menu
		String voice;
		if(AppModel.getVoice().equals("default")){
			voice = "voice_kal_diphone";
		}else{
			voice = "voice_akl_nz_jdt_diphone";
		}
		
		//Writes festival commands to use the selected voice and say desired word aloud
		printWriter.println("("+voice+")");
		printWriter.println("(SayText \"" + word + "\")");

		printWriter.close();
	}
}
