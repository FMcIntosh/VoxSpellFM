package app.model;

import app.AppModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


/**
 * Created by Fraser McIntosh on 13/09/2016.
 */
public class QuizModel {

    private int _numWordsInQuiz;
    private int _numCorrectWords;
    private LevelModel _levelSelected;
    private ArrayList<WordModel> _quizWords;
    private boolean _isReview;
    private int _curruntWordIndex;
    private QuizState _quizState;
    private WordModel _wordModel;
    private int MAX_QUIZ_WORDS = 10;
    private static final int PASS_LEVEL_SCORE = 9;
    private boolean _successfulQuiz = false;
    private boolean _perfectQuiz = false;
    private boolean _isHardestLevel;


    public QuizModel(boolean isReview, LevelModel levelSelected) {
        _isReview = isReview;
        _levelSelected = levelSelected;
        if(getLevelSelected().equals(LevelModel.get(AppModel.getLevelsUnlocked()-1)) && AppModel.getLevelsUnlocked() < AppModel.getNumLevels()) {
            _isHardestLevel = true;
        } else {
            _isHardestLevel = false;
        }
    }

    // Allow custom no. of words in a quiz
    public QuizModel(boolean isReview, LevelModel levelSelected, int numWordsInQuiz) {
        this(isReview, levelSelected);
        MAX_QUIZ_WORDS = numWordsInQuiz;
    }


    // Starts the quiz, initialising values
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
    private ArrayList<WordModel> generateQuizWords() {
        ArrayList<WordModel> quizWords = new ArrayList<>();
        String filePath = SpellingListModel.getPath();
        if(_isReview) {
            filePath = WordFile.REVIEW + "";
        }
        ArrayList<String> wordsFromList= FileModel.getWordsFromLevel(filePath, getLevelSelected().getLevelAsInt());
        int numWordsInQuiz = MAX_QUIZ_WORDS;
        if(wordsFromList.size() < MAX_QUIZ_WORDS) {
            numWordsInQuiz = wordsFromList.size();
        }
        int count = 0;

        while(count <MAX_QUIZ_WORDS) {
            HashSet<String> wordsInQuiz = new HashSet<>();
            for (int i = 0; i < numWordsInQuiz; i++) {
                // Decide what file to take from

                // Take a random word
                int index = new Random().nextInt((wordsFromList.size()));
                String word = wordsFromList.get(index);
                while (wordsInQuiz.contains(word)) {
                    index = new Random().nextInt((wordsFromList.size()));
                    word = wordsFromList.get(index);
                }
                wordsInQuiz.add(word);
                quizWords.add(new WordModel(word));
                count++;
                if(count ==MAX_QUIZ_WORDS) break;
            }
        }
        return quizWords;
    }

    // Getters -----------------------------------------------------------------------------------------

    public int getNumWordsInQuiz() {
        return _numWordsInQuiz;
    }
    public LevelModel getLevelSelected() {
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
        return _quizWords.get(_curruntWordIndex).getWord();
    }
    public boolean getIsHardestLevel() {
        return _isHardestLevel;
    }
    public ArrayList<WordModel> getQuizWords() {
        return _quizWords;
    }

    // End of getters ------------------------------------------------------------------------------------------



    /*
     * Update the current state of the quiz, including the state of the word
     */
    public void updateQuizState() {
        // If the word is failed or mastered, it is finished so need to go to the next word
            addWordToFiles();
            _curruntWordIndex++;

            if(_wordModel.getWordState().equals(WordState.CORRECT)) {
                _numCorrectWords++;
                AppModel.increaseCurrentSreak();
            }
        // If we have gone through all words in the quiz, the quiz is finished

        if(_numWordsInQuiz == _curruntWordIndex){
            _quizState = QuizState.FINISHED;
            // If passed quiz
            if(getNumCorrectWords() >= PASS_LEVEL_SCORE){
                _successfulQuiz = true;
                //if perfect quiz
                if(getNumCorrectWords() == MAX_QUIZ_WORDS) {
                    _perfectQuiz = true;

                    //only progress levels if in main quiz mode
                    if(!_isReview) {
                        _levelSelected.nextLevel();
                    }
                }

                // Increment level
                try {
                    // If current level is highest unlocked level
                    // And not the highest level possible
                    if(_isHardestLevel) {
                        // unlock the next level
                        AppModel.setLevelsUnlocked(AppModel.getLevelsUnlocked() + 1);
                    }
                } catch (FileNotFoundException e ){}
            }
        } else {
            _wordModel = _quizWords.get(_curruntWordIndex);
        }
    }

    /*
     * Called when moving to next word.
     */
    private void addWordToFiles() {
        switch (_wordModel.getWordState()) {
            case INCORRECT:
                FileModel.addWordToLevel(WordFile.FAILED.getPath(), _wordModel.getWord(), getLevelSelected().getLevelAsInt());
                // Add both faulted and failed words to review list
                FileModel.addUniqueWordToLevel(WordFile.REVIEW.getPath(), _wordModel.getWord(), getLevelSelected().getLevelAsInt());
                break;
            case CORRECT:
                // if mastered add to mastered list and remove from review list
                FileModel.addWordToLevel(WordFile.MASTERED.getPath(), _wordModel.getWord(), getLevelSelected().getLevelAsInt());
                FileModel.removeWordFromLevel(WordFile.REVIEW.getPath(), _wordModel.getWord(), getLevelSelected().getLevelAsInt());
                break;
            default:
        }

        FileModel.addUniqueWordToLevel(WordFile.ATTEMPTED.getPath(), getCurrentWord(), getLevelSelected().getLevelAsInt());
    }


    // Answer submission logic ---------------------------------------------------------------------------------
    public boolean submitAnswer (String answer) {
        //Verify valid
        if(!answer.matches("[a-zA-Z]+")){
            return false;
        } else {
            //update app.model state by passing through the answer result (true/false)

            _wordModel.updateWordState(answer.toLowerCase().equals(getCurrentWord().toLowerCase()));
           _quizWords.set(_curruntWordIndex, _wordModel);
            updateQuizState();
        }
        // Return a true response to the view if successful submission
        return true;
    }
}
