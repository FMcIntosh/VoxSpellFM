package app.scene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Fraser McIntosh on 18/09/2016.
 */
public class AlertBox {

    private Stage _window;
    private String _message;


    public AlertBox(String message) {
        _message = message;
    }
    public Scene build() {
        //Clear history, maybe could be in a better place in terms of responsibilities
        _window = new Stage();

        //Block user interaction with other windows until this window is
        // dealt with
        _window.initModality(Modality.APPLICATION_MODAL);
        _window.setMinWidth(300);

        //Components
        Label label = new Label();
        label.setText(_message);
        Button closeButton = new Button("OK");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                _window.close();
            }
        });


        //Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Scene
        return new Scene(layout, 400, 100);

    }

    public void setScene() {
        Scene scene = build();
        _window.setScene(scene);
        //Needs to be closed before returning
        _window.showAndWait();
    }
}
