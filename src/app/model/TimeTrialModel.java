package app.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Fraser McIntosh on 6/10/2016.
 */
public class TimeTrialModel extends QuizModel {
    private static final int NUM_WORDS = 200;
    TimeTrialModel(LevelModel levelSelected) {
        super(false, levelSelected);
    }

    private ArrayList<WordModel> generateQuizWords() {
        ArrayList<WordModel> quizWords = new ArrayList<>();
        WordFile file = WordFile.SPELLING_LIST;

        int numWordsInQuiz = 0;
        while(numWordsInQuiz < NUM_WORDS) {
            ArrayList<String> wordsFromList = FileModel.getWordsFromLevel(file, getLevelSelected().getLevelAsInt());
            for (int i = 0; i < wordsFromList.size(); i++) {
                // Decide what file to take from
                // Take a random word
                int index = new Random().nextInt((wordsFromList.size()));
                String word = wordsFromList.get(index);
                while (quizWords.contains(word)) {
                    index = new Random().nextInt((wordsFromList.size()));
                    word = wordsFromList.get(index);

                }
                quizWords.add(new WordModel(word));
                numWordsInQuiz++;
                if(numWordsInQuiz == NUM_WORDS) break;
            }
        }
        return quizWords;
    }
}
