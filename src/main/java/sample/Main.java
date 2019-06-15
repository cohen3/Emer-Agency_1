package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.ModelLogic.Controller;

//import sample.Enums.Fields;

public class Main extends Application {
    // a boolean variable to enable various in code debug
    static final boolean _DEBUG = true;

    //TODO: modify this method to run our new window (this is a garbage code but can use it as a reference)
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(this.getClass().getResource("MainWindow.fxml").openStream());
        MainWindowView mwv = fxmlLoader.getController();
        primaryStage.setTitle("Emer-Agency");
        Scene scene = new Scene(root, 800, 400);
//        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("logo.jpeg")));
//        primaryStage.show();


//        MainWindowView control=new MainWindowView();
        Controller m = new Controller("Database/new.db");
        mwv.setModel(m);
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        Parent root = fxmlLoader.load(getClass().getResource("../MainWindow.fxml").openStream());
//        primaryStage.setTitle("Emer-Agency");
//        scene.getStylesheets().add(getClass().getResource("../style.css").toExternalForm());
//        primaryStage.setScene(scene);
//        control.setModel(new Controller("Database/EmerAgencyDB.db"));//setting a Controller for it
//        primaryStage.setOnCloseRequest((WindowEvent event1) -> {
//            control.exit();
//        });

        primaryStage.show();


    }


    public static void main(String[] args) {
        //ValidateDatabase("Database/EmerAgencyDB.db");
        //TODO: when app is opening, read database and build the data structures
        launch(args);
       // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
       // Date date = new Date();
//      //  System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
//        Controller c = new Controller("Database/EmerAgencyDB.db");
//        ArrayList<Pair> p = new ArrayList<>();
//        Pair a = new Pair(Fields.userID, "EladC");
//        p.add(a);
//        ArrayList<HashMap<String, String>> results = c.ReadEntries(p, Tables.Events);
//        HashMap<String, String> h1 = results.get(0);
//        Event e = new Event(h1.get("eventID"),h1.get("date"),h1.get("information"),h1.get("status"),h1.get("userID"));
//        LinkedList<Update> arr = new LinkedList<>();
//        p.clear();
//        a = new Pair(Fields.eventID,e.eventID);
//        p.add(a);
//        ArrayList<HashMap<String, String>> results2 = c.ReadEntries(p, Tables.UserUpdates);
//        for (HashMap<String, String> x:results2) {
//            Update d = new Update(x.get("information"),x.get("date"));
//            arr.add(d);
//        }
//        e.addUpdates(arr);
//
//        LinkedList<Update> pr = e.getSortedUpdates();
//        System.out.println(pr);
    }

}

