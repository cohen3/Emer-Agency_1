package sample.ModelLogic;

import javafx.util.Pair;
import sample.*;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

//import sample.Enums.Fields;

public class Controller {
    public static DBHandler dbHandler = null;
    HashMap<String, Organization> organizations;
    User currentLogged;
    //    private Organization organization;
//    private HashMap<String, Event> events;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public Controller(String path) {
        dbHandler = DBHandler.getInstance();
        dbHandler.connectDB(path);
        organizations = AOrganizationListFactory.generateList();

//        Pair p = new Pair(Fields.status, "active");
//        ArrayList<Pair> a = new ArrayList<>();
//        a.add(p);
//        ArrayList<HashMap<String, String>> results = dbHandler.ReadEntries(a, Tables.Users);
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
//        this.organizations.put("o1", new Organization(admin, "o1"));

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

        ArrayList<HashMap<String, String>> results = dbHandler.ReadEntries(l, Tables.Users);
        if(results.size()==0){ return RESULT.Fail;}
        HashMap<String, String>  r = results.get(0);
        String n = r.get(Fields.organization.name());
        Organization o = organizations.get(n);
        if(!o.checkUser(results.get(0).get(Fields.userID)))
        {
            currentLogged = o.AddUser(results.get(0));
        }
        return (results.size() == 1) ? RESULT.Success : RESULT.Fail;
//        return RESULT.Success;
    }

    public RESULT checkMokdan(String username) {
        ArrayList<Pair> l = new ArrayList<>();
        l.add(new Pair("userID", username));
        l.add(new Pair("organization", "agent"));

        return (dbHandler.ReadEntries(l, Tables.Users).size() == 1) ? RESULT.Success : RESULT.Fail;
    }

    public String returnForce(String username) {
        ArrayList<Pair> l = new ArrayList<>();
        l.add(new Pair("userID", username));
//        l.add(new Pair("organization", "agent"));

        return (dbHandler.ReadEntries(l, Tables.Users).get(0).get("organization")) ;
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
            ArrayList<Pair> l2 = new ArrayList<>();
            l2.add(new Pair("eventID", element.get("eventID")));

            ArrayList<HashMap<String, String>> Acategories = dbHandler.ReadEntries(l2, Tables.EventCategory);
            ArrayList<HashMap<String, String>> Aforces = dbHandler.ReadEntries(l2, Tables.EventForces);

            ArrayList<Object> categories = new ArrayList<>();
            ArrayList<Object> forces = new ArrayList<>();

            for (HashMap<String, String> element2 : Acategories) {
                categories.add(element2.get("category"));
            }
            for (HashMap<String, String> element2 : Aforces) {
                forces.add(element2.get("force"));
            }

            Event event = new Event(element.get("eventID"), element.get("date"), element.get("information"), element.get("status"), element.get("UserID"),forces,categories);
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
        if(currentLogged.getEvent(eventID) == null){
            ArrayList<Pair> l1 = new ArrayList<>();
            l.add(new Pair("eventID", eventID));
            ArrayList<HashMap<String, String>> results = dbHandler.ReadEntries(l1,Tables.Events);
            HashMap<String, String> h = results.get(0);
            ArrayList<Pair> l3 = new ArrayList<>();
            l3.add(new Pair("eventID", eventID));

            ArrayList<HashMap<String, String>> Acategories1 = dbHandler.ReadEntries(l3, Tables.EventCategory);
            ArrayList<HashMap<String, String>> Aforces1 = dbHandler.ReadEntries(l3, Tables.EventForces);

            ArrayList<Object> categories = new ArrayList<>();
            ArrayList<Object> forces = new ArrayList<>();

            for (HashMap<String, String> element2 : Acategories1) {
                categories.add(element2.get("category"));
            }
            for (HashMap<String, String> element2 : Aforces1) {
                forces.add(element2.get("force"));
            }
            Event e = new Event(eventID,h.get(Fields.date.name()),
                    "",Fields.status.name(),userID,forces,categories);
            currentLogged.AddEvent(e);
        }
        currentLogged.getEvent(eventID).addUpdate(new Update(information, dtf.format(LocalDateTime.now())));
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
        ArrayList<Object> cats = new ArrayList<>();
        ArrayList<Object> fs = new ArrayList<>();

        RESULT r;
        if ((r = dbHandler.AddEntry(l, Tables.Events)).equals(RESULT.Success)) {

            for (Object category : categories) {
                l.clear();
                l.add(new Pair("category", category.toString()));
                l.add(new Pair("eventID", eventID));
                dbHandler.AddEntry(l, Tables.EventCategory);
                cats.add(category);
            }

            for (Object force : forces) {
                l.clear();
                l.add(new Pair("force", force.toString()));
                l.add(new Pair("eventID", eventID));
                dbHandler.AddEntry(l, Tables.EventForces);
                fs.add(force);
            }
            AddUpdate(userID, eventID, firstupdateinformation);
            Event e = new Event(eventID,dtf.format(LocalDateTime.now()),
                    "",status,userID,fs,cats);
            currentLogged.AddEvent(e);
        }

        return r;
    }
}
