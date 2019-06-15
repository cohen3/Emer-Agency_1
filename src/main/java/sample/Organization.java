package sample;

import sample.Enums.Fields;
import sample.ModelLogic.DBHandler;

import java.util.HashMap;
import java.util.HashSet;

public class Organization {

    public static DBHandler dbHandler = null;
    String name;
    AUserFactory userFactory;
    User admin = null;
    HashMap<String, User> users;
    HashSet<Event> events;

    public Organization(User admin, String name) {
        dbHandler = DBHandler.getInstance();
        this.name = name;
        this.userFactory = new AUserFactory();
        this.admin = admin;
        this.events = new HashSet<>();
        this.users = new HashMap<>();
        users.put(this.admin.getID(), this.admin);
    }

//    private void LoadOrganization()
//    {
//        ArrayList<Pair> fnv = new ArrayList<>();
//        fnv.add(new Pair(Fields.organization, this.name));
//        ArrayList<HashMap<String, String>> res =
//                dbHandler.ReadEntries(fnv,Tables.Users);
//        for(HashMap<String, String> entry : res){
//            ArrayList<Pair> eventsOnUser = new ArrayList<>();
//            eventsOnUser.add(new Pair("userID",entry.get(Fields.userID.name())));
//            ArrayList<HashMap<String, String>> res2 =
//                    dbHandler.ReadEntries(eventsOnUser, Tables.Events);
//            for(HashMap<String, String> entry2 : res2) {
//
//            }
//        }
//    }

    public Organization(String name)
    {
        dbHandler = DBHandler.getInstance();
        this.name = name;
        this.userFactory = new AUserFactory();
        this.events = new HashSet<>();
        this.users = new HashMap<>();
    }

    public void SetAdmin(User admin)
    {
        this.admin = admin;
    }

    public String getNane(){return this.name;}

    public User getUser(int ID) {
        return users.get(ID);
    }

    public User AddUser(HashMap<String, String> details) {
        User u = userFactory.generate(details);
        if(details.get(Fields.kind.name()).equals("admin") && admin == null)
            SetAdmin(u);
        return AddUser(u);
    }

    public User AddUser(User u)
    {
        users.put(u.getID(), u);
        return u;
    }

    public void AddEventToUser(String ID, Event e)
    {

    }

    public boolean checkUser(String ID)
    {
        return users.containsKey(ID+"");
    }
}
