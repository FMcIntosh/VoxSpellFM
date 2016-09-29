package app.model;

/**
 * Created by Fraser McIntosh on 16/09/2016.
 */
public enum WordFile {


    SPELLING_LIST("NZCER-spelling-lists.txt"),
    FAULTED(".app_files/.faulted_stats.txt"),
    FAILED(".app_files/.failed_stats.txt"),
    MASTERED(".app_files/.mastered_stats.txt"),
    REVIEW(".app_files/.reviewlist.txt"),
    ATTEMPTED(".app_files/.attempted.txt");

    String _filename;
    private WordFile(String filename) {
        _filename = filename;
    }

    @Override
    public String toString() {
        return _filename;
    }
}
