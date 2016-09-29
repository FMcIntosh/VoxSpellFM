package app.model;

import app.AppModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Fraser McIntosh on 13/09/2016.
 */
public class QuizModel {

    private int _numWordsInQuiz;
    private int _numCorrectWords;
    private int _levelSelected;
    private ArrayList<String> _quizWords;
    private boolean _isReview;
    private int _curruntWordIndex;
    private QuizState _quizState;
    private WordModel _wordModel;
    private static final int MAX_QUIZ_WORDS = 10;
    private static final int PASS_LEVEL_SCORE = 9;
    private boolean _successfulQuiz = false;
    private boolean _isHardestLevel;


    public QuizModel(boolean isReview, int levelSelected) {
        _isReview = isReview;
        _levelSelected = levelSelected;
        if(getLevelSelected() == AppModel.getLevelsUnlocked() && AppModel.getLevelsUnlocked() < AppModel.getNumLevels()) {
            _isHardestLevel = true;
        } else {
            _isHardestLevel = false;
        }
    }

    public QuizState start() {
        _curruntWordIndex = 0;
        _quizWords = generateQuizWords();
        _numWordsInQuiz = _quizWords.size();
        _numCorrectWords = 0;

        if (_numWordsInQuiz > 0) {
            _quizState = QuizState.READY;
            _wordModel = new WordModel(getCurrentWord());
        } else _quizState = QuizState.NO_WORDS;
        return _quizState;
    }

    /*
     * Helper method for constructor that generates the words for a quiz, utilisting app.model.FileModel's
     * methods for getting words.
     */
    private ArrayList<String> generateQuizWords() {
        ArrayList<String> quizWords = new ArrayList<>();
        WordFile file = WordFile.SPELLING_LIST;
        if(_isReview) {
            file = WordFile.REVIEW;
        }
        ArrayList<String> wordsFromList= FileModel.getWordsFromLevel(file, getLevelSelected());
        int numWordsInQuiz = MAX_QUIZ_WORDS;
        if(wordsFromList.size() < MAX_QUIZ_WORDS) {
            numWordsInQuiz = wordsFromList.size();
        }
        for(int i = 0; i < numWordsInQuiz; i++) {
            // Decide what file to take from

            // Take a random word
            int index = new Random().nextInt((wordsFromList.size()));
            String word = wordsFromList.get(index);
            while(quizWords.contains(word)) {
                 index = new Random().nextInt((wordsFromList.size()));
                word = wordsFromList.get(index);

            }
            quizWords.add(word);
        }
        return quizWords;
    }

    // Getters -----------------------------------------------------------------------------------------

    public int getNumWordsInQuiz() {
        return _numWordsInQuiz;
    }
    public int getLevelSelected() {
        return _levelSelected;
    }
    public WordState getWordState() {
        return _wordModel.getWordState();
    }
    public boolean getIsReview() {
        return _isReview;
    }
    public int getCurruntWordIndex() {
        return _curruntWordIndex;
    }
    public QuizState getQuizState(){
        return _quizState;
    }
    public int getNumCorrectWords() {
        return _numCorrectWords;
    }
    public boolean getSuccessfulQuiz() {
        return _successfulQuiz;
    }
    public String getCurrentWord() {
        return _quizWords.get(_curruntWordIndex);
    }
    public boolean getIsHardestLevel() {
        return _isHardestLevel;
    }

    // End of getters ------------------------------------------------------------------------------------------



    /*
     * Update the current state of the quiz, including the state of the word
     */
    public void updateQuizState() {
        // If the word is failed or mastered, it is finished so need to go to the next word
        if(!(_wordModel.getWordState().equals(WordState.INCORRECT))) {
            addWordToFiles();
            _curruntWordIndex++;
            if(!_wordModel.getWordState().equals(WordState.FAILED)) {
                _numCorrectWords++;
            }
        }
        // If we have gone through all words in the quiz, the quiz is finished

        if(_numWordsInQuiz == _curruntWordIndex){
            _quizState = QuizState.FINISHED;
            if(getNumCorrectWords() >= PASS_LEVEL_SCORE){
                _successfulQuiz = true;
                try {
                    // If current level is highest unlocked level
                    // And not the highest level possible
                    if(_isHardestLevel) {
                        // unlock the next level
                        AppModel.setLevelsUnlocked(AppModel.getLevelsUnlocked() + 1);
                    }
                } catch (FileNotFoundException e ){}
            }
        }
    }

    /*
     * Called when moving to next word.
     */
    private void addWordToFiles() {
        switch (_wordModel.getWordState()) {
            case FAULTED:
                FileModel.addWordToLevel(WordFile.FAULTED, _wordModel.getWord(), getLevelSelected());
            case FAILED:
                FileModel.addWordToLevel(WordFile.FAILED, _wordModel.getWord(), getLevelSelected());
                // Add both faulted and failed words to review list
                FileModel.addUniqueWordToLevel(WordFile.REVIEW, _wordModel.getWord(), getLevelSelected());
                break;
            case MASTERED:
                // if mastered add to mastered list and remove from review list
                FileModel.addWordToLevel(WordFile.MASTERED, _wordModel.getWord(), getLevelSelected());
                FileModel.removeWordFromLevel(WordFile.REVIEW, _wordModel.getWord(), getLevelSelected());
                break;
            default:
        }

        FileModel.addUniqueWordToLevel(WordFile.ATTEMPTED, getCurrentWord(), getLevelSelected());
    }


    public void nextWord() {
        _wordModel = new WordModel(getCurrentWord());
    }
    // Answer submission logic ---------------------------------------------------------------------------------

    public boolean submitAnswer (String answer) {
        //Verify valid
        if(!answer.matches("[a-zA-Z]+")){
            return false;
        } else {
            //update app.model state by passing through the answer result (true/false)
            _wordModel.updateWordState(answer.toLowerCase().equals(getCurrentWord().toLowerCase()));
            updateQuizState();
        }
        // Return a true response to the view if successful submission
        return true;
    }
}
