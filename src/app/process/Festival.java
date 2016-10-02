package app.process;

import java.io.IOException;
import java.io.PrintWriter;

import app.AppModel;

public class Festival {
	
	
	//Says aloud the given word
	public static void sayWord(String word) throws IOException, InterruptedException{
		createTempScript(word);
				
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", "festival -b .app_files/.word.scm");
		builder.start();
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
