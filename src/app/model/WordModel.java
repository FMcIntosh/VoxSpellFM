package app.model;

/**
 * Created by Fraser McIntosh on 16/09/2016.
 */
public class WordModel {

    private WordState _wordState;
    String _word;

    WordModel(String word) {
        _wordState = WordState.STARTED;
        _word = word;
    }
    public WordState getWordState(){
        return _wordState;
    }

    public String getWord() {
        return _word;
    }

    public void updateWordState(boolean isCorrectAnswer) {
                if(isCorrectAnswer) {
                    _wordState = WordState.CORRECT;
                } else {
                    _wordState = WordState.INCORRECT;
                }
    }
}
