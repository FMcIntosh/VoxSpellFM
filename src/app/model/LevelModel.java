package app.model;

/**
 * Created by Fraser McIntosh on 3/10/2016.
 */
public class LevelModel {
    String _name;
    int _timesCompleted;
    LevelModel(String name, int timesCompleted) {
        _name = name;
        _timesCompleted = timesCompleted ;
    }

    @Override
    public String toString() {
        return this._name;
    }
}
