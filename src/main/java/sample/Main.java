package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.Tables;
import sample.ModelLogic.Controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Main extends Application {
    // a boolean variable to enable various in code debug
    static final boolean _DEBUG = true;

    //TODO: modify this method to run our new window (this is a garbage code but can use it as a reference)
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(this.getClass().getResource("MainWindow.fxml").openStream());
        MainWindowView mwv = fxmlLoader.getController();
        primaryStage.setTitle("DRE Project");
        Scene scene = new Scene(root, 1000, 780);
        primaryStage.setScene(scene);
//        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
        primaryStage.setResizable(true);
//        primaryStage.show();


//        MainWindowView control=new MainWindowView();
        Controller m = new Controller("Database/EmerAgencyDB.db");
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
      //  System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        Controller c = new Controller("Database/EmerAgencyDB.db");
        ArrayList<Pair> p = new ArrayList<>();
        Pair a = new Pair(Fields.userID, "EladC");
        p.add(a);
        ArrayList<HashMap<String, String>> results = c.ReadEntries(p, Tables.Events);
        HashMap<String, String> h1=results.get(0);
        Event e = new Event(h1.get("eventID"),h1.get("date"),h1.get("information"),h1.get("status"),h1.get("userID"));
        LinkedList<Update> arr = new LinkedList<>();
        p.clear();
        a = new Pair(Fields.eventID,e.eventID);
        p.add(a);
        ArrayList<HashMap<String, String>> results2 = c.ReadEntries(p, Tables.UserUpdates);
        for (HashMap<String, String> x:results2) {
            Update d = new Update(x.get("information"),x.get("date"));
            arr.add(d);
        }
        e.addUpdates(arr);

        LinkedList<Update> pr = e.getSortedUpdates();
        System.out.println(pr);



    }

    /**
     * this method checks if a database exist, creates a new database in the given name if
     * database does not exist.
     * @param db_name - the name of the database we wish to check
     */
    public static void ValidateDatabase(String db_name) {
        Connection c = null;                                    //this object holds the connection to the database

        File db = new File(db_name);                            //a file object to point to the database file
        if (!db.exists()) {                                     //checks if the database exist, if not than creates it
            try {                                               //creates a new database inside the Database directory
                c = DriverManager.getConnection("jdbc:sqlite:" + db_name);
                Statement stmt = c.createStatement();
                String sql = "CREATE TABLE USERS " +            //creates a users table
                        "(userName VARCHAR(20) PRIMARY KEY     NOT NULL," +
                        " password         TEXT(20)    NOT NULL, " +
                        " birthDate        DATE        NOT NULL, " +
                        " firstName        VARCHAR(20) NOT NULL, " +
                        " lastName         VARCHAR(20) NOT NULL," +
                        " homeTown         VARCHAR(20) NOT NULL";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
        if (_DEBUG)                                              //for debugging purposes
            System.out.println("Opened database successfully");
    }
}

