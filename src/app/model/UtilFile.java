package app.model;

/**
 * Created by Fraser McIntosh on 3/10/2016.
 *
 * Enum to store paths to utility files
 */
public enum UtilFile {
    LEVELS(".app_files/level_data.txt"),
    TIMETRIAL(".app_files/timetrial_data.txt");

    String _filename;
    private UtilFile(String filename) {
        _filename = filename;
    }

    @Override
    public String toString() {
        return _filename;
    }
}
