package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.controlsfx.control.CheckListView;
import sample.Enums.RESULT;
import sample.ModelLogic.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MainWindowView implements Initializable {
    public TextField usernameSign;
    public PasswordField passwordSign;
    public Tab signTab;
    public Tab PublishEventTab;
    public Tab ShowEventHistoryTab;
    public Tab AddUpdateTab;
    public TabPane tabPane;
    public CheckListView<String> ForcesCreate;
    public CheckListView<String> CategoriesCreate;
    public TextArea FirstUpdateCreate;
    public TextField TitleCreate;
    public TableColumn<Event,String> eventID;
    public TableColumn<Event,String> Title;
    public TableColumn<Event,String> publisher;
    public TableColumn<Event,String> showButtons;
    public TableView<Event> tableViewEvents;
    public ComboBox eventIDtoUpdate;
    public TextArea newUpdateInformation;

    // Usecases we will implement:
    //
    // 1) UC1: add update to an event
    // 2) UC2: view update history
    // 3) UC3: publish an event

    User logged = null;

    Controller m;
//    @FXML
//    Button btn;
//    @FXML
//    Label lbl;
    String currentUsername = "guest";

    public void setModel(Controller m) {
        this.m = m;
        ArrayList<Event> events = m.getEvents();
        tableViewEvents.getItems().addAll(events);
        for (Event event : events)
            eventIDtoUpdate.getItems().add(event.eventID);
    }

    public void onClickEvent(ActionEvent actionEvent) throws Exception {
//        lbl.setText("clicked");
        RESULT r = m.login(usernameSign.getText(), passwordSign.getText());
        if (r.toString().equals(RESULT.Success.toString())) {
            tabPane.getTabs().addAll(AddUpdateTab,ShowEventHistoryTab);
            tabPane.getTabs().removeAll(signTab);
            currentUsername = usernameSign.getText();
            if(m.checkMokdan(usernameSign.getText()).equals(RESULT.Success))
                tabPane.getTabs().add(PublishEventTab);
        } else if (r.toString().equals(RESULT.Fail.toString())) {
            Alert.AlertType alertAlertType;
            Alert alert = new Alert(AlertType.ERROR,"username or password does not exist");
            alert.showAndWait();
        }else {
            throw new Exception("wat");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.getTabs().removeAll(PublishEventTab,AddUpdateTab,ShowEventHistoryTab);
        CategoriesCreate.getItems().clear();
        for (String checkedItem : CategoriesCreate.getCheckModel().getCheckedItems()) {
            CategoriesCreate.getItems().remove(checkedItem);
        }
        CategoriesCreate.getItems().addAll("assault","robbery","fire","car crash");
        ForcesCreate.getItems().clear();
        for (String checkedItem : ForcesCreate.getCheckModel().getCheckedItems()) {
            ForcesCreate.getItems().remove(checkedItem);
        }
        ForcesCreate.getItems().addAll("Police","Firefighters","Magen David Adom");

        tableViewEvents.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        eventID.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().eventID));
        publisher.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().operator));
        Title.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().header));
        showButtons.setCellFactory(param -> new TableCell<Event,String>(){
            final Button btn = new Button("show updates");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btn.setOnAction(event -> {
                        Event event1 = getTableView().getItems().get(getIndex());
                        Stage stage = new Stage();
                        stage.setAlwaysOnTop(false);
                        stage.setResizable(false);
                        stage.setTitle("event " + event1.eventID);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        ScrollPane scrollPane = new ScrollPane();
                        TableView<Update> updatesTable = getUpdatesTable();
                        updatesTable.getItems().addAll(m.getEventUpdates(event1.eventID));////
                        scrollPane.setContent(updatesTable);
                        Scene scene = new Scene(scrollPane);
                        stage.setScene(scene);
                        stage.show();
                    });
                    setGraphic(btn);
                    setText(null);
                }
            }
        });

//        tableViewEvents.getItems().addAll(m.getEvents());
    }

    private TableView<Update> getUpdatesTable() {
        TableView<Update> updatesTable = new TableView<>();
        updatesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Update, String> dateColumn = new TableColumn<>("date");
        dateColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().date));

        TableColumn<Update, String> informationColumn = new TableColumn<>("information");
        informationColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().Information));

        updatesTable.getColumns().addAll(dateColumn,informationColumn);
        return updatesTable;
    }

    public void updateEvent(ActionEvent actionEvent) {
        m.AddUpdate(currentUsername, eventIDtoUpdate.getSelectionModel().getSelectedItem().toString(),newUpdateInformation.getText());
    }

    public void createEvent(ActionEvent actionEvent) {
        m.AddEvent(currentUsername, TitleCreate.getText(),CategoriesCreate.getCheckModel().getItem(0),"in progress",FirstUpdateCreate.getText());

    }
}
