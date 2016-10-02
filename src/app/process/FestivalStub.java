package app.process;

/**
 * Created by Fraser McIntosh on 21/09/2016.
 */
/**
 * Created by Fraser McIntosh on 17/09/2016.
 */
public class FestivalStub {

    //Used to confirm festival is saying correct word

    //Displays the given word in console
    public static void sayWord(String word){
        System.out.println(word);
    }

    //Displays the given word, spaced out, in console
    public static void spellWord(String word){
        word = word.replaceAll(".(?!$)", "$0 ");
        System.out.println(word);
    }

}
