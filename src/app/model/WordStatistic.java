package app.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import app.model.FileModel;
import app.model.WordFile;

/**
 * Created by Fraser McIntosh on 20/08/2016.
 */
public class WordStatistic {
    private SimpleStringProperty word;
    private SimpleIntegerProperty failed;
    private SimpleIntegerProperty mastered;

    /*
     * Pass in the word to the constructor and sets the fields
     */
    WordStatistic(String word, int level) {
        this.word = new SimpleStringProperty(word);
        failed = new SimpleIntegerProperty(FileModel.countOccurencesInLevel(WordFile.FAILED, word, level));
        mastered = new SimpleIntegerProperty(FileModel.countOccurencesInLevel(WordFile.MASTERED, word, level));
    }

    public String getWord() {
        return word.get();
    }

    public void setWord(String word) {
       this.word.set(word);
    }


    public int getFailed() {
        return failed.get();
    }

    public void setFailed(int failed) {
        this.failed.set(failed);
    }

    public int getMastered() {
         return mastered.get();
    }

    public void setMastered(int mastered) {
       this.mastered.set(mastered);
    }
}
