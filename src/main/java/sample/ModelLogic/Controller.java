package sample.ModelLogic;

import javafx.util.Pair;
//import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;
import sample.Event;
import sample.Organization;
import sample.Update;
import sample.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
    public static DBHandler dbHandler = null;
//    private Organization organization;
//    private HashMap<String, Event> events;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public Controller(String path) {
        dbHandler = new DBHandler();
        dbHandler.connectDB(path);
//        Pair p = new Pair(Fields.status, "active");
//        ArrayList<Pair> a = new ArrayList<>();
//        a.add(p);
//        ArrayList<HashMap<String, String>> results = ReadEntries(a, Tables.Users);
//        User admin;
//        if (results.size() < 1) admin = new User("1", "10", "0", "active", "EladC", "1234");
//        else {
//            HashMap<String, String> res = results.get(0);
//            admin = new User(res.get(Fields.userID.name()),
//                    res.get(Fields.rank.name()),
//                    res.get(Fields.warning.name()),
//                    res.get(Fields.status.name()),
//                    res.get(Fields.Username.name()),
//                    res.get(Fields.password.name()));
//        }
//        this.organization = new Organization(admin);

    }


//    public RESULT AddEntry(ArrayList<Pair> fieldsNvalues, Tables table) {
//        return dbHandler.AddEntry(fieldsNvalues, table);
//    }
//
//    public ArrayList<HashMap<String, String>> ReadEntries(ArrayList<Pair> fieldsNvalues, Tables table) {
//        return dbHandler.ReadEntries(fieldsNvalues, table);
//    }
//
//    public RESULT UpdateEntries(Tables table, Fields fieldToUpdate, String newValue, ArrayList<Pair> fieldsNvalues) {
//        return dbHandler.UpdateEntries(table, fieldToUpdate, newValue, fieldsNvalues);
//    }
//
//    public RESULT DeleteEntry(Tables table, ArrayList<Pair> fieldValues) {
//        return dbHandler.DeleteEntry(table, fieldValues);
//    }

    public RESULT login(String username, String password) {
        ArrayList<Pair> l = new ArrayList<>();
        l.add(new Pair("userID", username));
        l.add(new Pair("password", password));


        return (dbHandler.ReadEntries(l, Tables.Users).size() == 1) ? RESULT.Success : RESULT.Fail;
//        return RESULT.Success;
    }

    public RESULT checkMokdan(String username) {
        ArrayList<Pair> l = new ArrayList<>();
        l.add(new Pair("userID", username));
        l.add(new Pair("organization", "agent"));

        return (dbHandler.ReadEntries(l,Tables.Users).size()==1)?RESULT.Success:RESULT.Fail;
//        return RESULT.Success;
    }

    public ArrayList<Update> getEventUpdates(String event_name) {
        ArrayList<Pair> l = new ArrayList<>();
        l.add(new Pair("eventID", event_name));
        ArrayList<Update> updates = new ArrayList<>();
        for (HashMap<String, String> element : dbHandler.ReadEntries(l, Tables.UserUpdates)) {
            Update update = new Update(element.get("information"), element.get("date"));
            updates.add(update);
        }
        return updates;
    }

    public ArrayList<Event> getEvents() {
        ArrayList<Pair> l = new ArrayList<>();
        ArrayList<Event> events = new ArrayList<>();
        for (HashMap<String, String> element : dbHandler.ReadEntries(l, Tables.Events)) {
            Event event = new Event(element.get("eventID"), element.get("date"), element.get("information"), element.get("status"), element.get("UserID"));
            events.add(event);
        }
        return events;
    }

    public RESULT AddUpdate(String userID, String eventID, String information) {
//        date=
        ArrayList<Pair> l = new ArrayList<>();
        l.add(new Pair("eventID", eventID));
        l.add(new Pair("information", information));
        l.add(new Pair("userID", userID));
        l.add(new Pair("date", dtf.format(LocalDateTime.now())));

        return dbHandler.AddEntry(l, Tables.UserUpdates);
    }

    public RESULT AddEvent(String userID, String eventID, String information, String status, Object[] categories, Object[] forces, String firstupdateinformation) {
//        date=
        ArrayList<Pair> l = new ArrayList<>();
        l.add(new Pair("eventID", eventID));
        l.add(new Pair("information", information));
        l.add(new Pair("userID", userID));
//        l.add(new Pair("category", category));
        l.add(new Pair("status", status));
        l.add(new Pair("date", dtf.format(LocalDateTime.now())));

        RESULT r;
        if( (r = dbHandler.AddEntry(l, Tables.Events)).equals(RESULT.Success))
        {

            for (Object category:categories) {
                l.clear();
                l.add(new Pair("category",category.toString()));
                l.add(new Pair("eventID",eventID));
                dbHandler.AddEntry(l,Tables.EventCategory);
            }

            for (Object force:forces)
            {
                l.clear();
                l.add(new Pair("force",force.toString()));
                l.add(new Pair("eventID",eventID));
                dbHandler.AddEntry(l,Tables.EventForces);
            }
            AddUpdate(userID,eventID,firstupdateinformation);
        }


        return r;
    }
}
