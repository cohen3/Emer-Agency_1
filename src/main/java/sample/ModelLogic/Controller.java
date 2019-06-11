package sample.ModelLogic;

import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;
import sample.Event;
import sample.Organization;
import sample.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
    public static DBHandler dbHandler =null;
    private Organization organization;
    private HashMap<String, Event> events;

    public Controller(String path)
    {
        this.dbHandler = new DBHandler();
        this.dbHandler.connectDB(path);
        Pair p = new Pair(Fields.status, "admin");
        ArrayList<Pair> a = new ArrayList<>();
        ArrayList<HashMap<String, String>> results = ReadEntries(a,Tables.Users);
        User admin;
        if(results.size() < 1) admin = new User("1", "10", "0", "admin", "EladC", "1234");
        else
        {
            HashMap<String, String> res = results.get(0);
            admin = new User(res.get(Fields.userID.name()),
                             res.get(Fields.rank.name()),
                             res.get(Fields.warning.name()),
                             res.get(Fields.status.name()),
                             res.get(Fields.Username.name()),
                             res.get(Fields.password.name()));
        }
        this.organization = new Organization(admin);

    }



    public RESULT AddEntry(ArrayList<Pair> fieldsNvalues, Tables table){return  dbHandler.AddEntry(fieldsNvalues,table);}
    public ArrayList<HashMap<String, String>> ReadEntries(ArrayList<Pair> fieldsNvalues, Tables table){ return dbHandler.ReadEntries(fieldsNvalues,table);}

    public RESULT UpdateEntries(Tables table, Fields fieldToUpdate, String newValue, ArrayList<Pair> fieldsNvalues){return dbHandler.UpdateEntries(table,fieldToUpdate,newValue,fieldsNvalues);}
    public RESULT DeleteEntry (Tables table, ArrayList<Pair> fieldValues){return dbHandler.DeleteEntry(table,fieldValues);}

    private void initiateData()
    {

    }
}
