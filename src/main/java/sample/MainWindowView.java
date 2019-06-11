package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.ModelLogic.*;

public class MainWindowView {
    // Usecases we will implement:
    //
    // 1) UC1: add update to an event
    // 2) UC2: view update history
    // 3) UC3: publish an event

    Controller m;
    @FXML
    Button btn;
    @FXML
    Label lbl;

    public void setModel(Controller m) {
        this.m = m;
    }

    public void onClickEvent(ActionEvent actionEvent) {
        lbl.setText("clicked");
    }
}
