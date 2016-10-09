package app.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Fraser McIntosh on 20/08/2016.
 */
public class Statistics {
	
	private int _level;
	
	public Statistics(int level){
		_level = level;
	}
    /*
     * Creates a list of word statistics using all the words in the attempted list
     * So doesn't create a statistic for a word that hasn't been attempted
     */
    public ObservableList<WordStatistic> getWordStatistics() {
        ObservableList<WordStatistic> statistics = FXCollections.observableArrayList();

        // BufferedReader reader = new BufferedReader(new FileReader(app.model.WordFile.ATTEMPTED + ""));
        ArrayList<String> levelWords = FileModel.getWordsFromLevel(WordFile.ATTEMPTED.getPath(), _level);

        //should read from file only the words between specified level and the next level
        for (String word : levelWords) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = word.trim();
            statistics.add(new WordStatistic(trimmedLine, _level));
        }

        /*
         * Sort the statistics by most times mastered
         */
        FXCollections.sort(statistics, new Comparator<WordStatistic>() {
            @Override
            public int compare(WordStatistic s1, WordStatistic s2) {
                if(s1.getMastered() < s2.getMastered()){
                    return 1;
                } else if (s1.getMastered()== s2.getMastered()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        return statistics;
    }

    @SuppressWarnings("unchecked")
	public ScrollPane constructTableLayout() {
    	//Creates word column
        TableColumn<WordStatistic, String> wordColumn = new TableColumn<>("Word");
        wordColumn.setMinWidth(200);
        wordColumn.setCellValueFactory(new PropertyValueFactory<WordStatistic, String>("word"));

        //Creates failed column
        TableColumn<WordStatistic, Integer> failedColumn = new TableColumn<>("Incorrect");
        failedColumn.setMinWidth(100);
        failedColumn.setCellValueFactory(new PropertyValueFactory<WordStatistic, Integer>("failed"));

        //Creates mastered column
        TableColumn<WordStatistic, Integer> masteredColumn = new TableColumn<>("Correct");
        masteredColumn.setMinWidth(100);
        masteredColumn.setCellValueFactory(new PropertyValueFactory<WordStatistic, Integer>("mastered"));

        //Create table out of the four columns
        TableView<WordStatistic> table = new TableView<>();
        table.setItems(getWordStatistics());
        table.getColumns().addAll(wordColumn, failedColumn, masteredColumn);

       //Create layout and add scroll pane and table as contents
        VBox root = new VBox();

        root.getChildren().addAll(table);
        root.setAlignment(Pos.CENTER);

        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setFitToWidth(true);
        scrollpane.setFitToHeight(true);
        scrollpane.setContent(root);
        return scrollpane;
    }


}