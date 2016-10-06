package app.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Fraser McIntosh on 6/10/2016.
 */
public class TimeTrialModel extends QuizModel {
    private static final int NUM_WORDS = 200;
    public TimeTrialModel(LevelModel levelSelected) {
        super(false, levelSelected, NUM_WORDS);
    }

}
