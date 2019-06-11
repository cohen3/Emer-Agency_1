package sample;

import java.util.HashMap;
import java.util.HashSet;

public class Organization {

    User admin;
    HashMap<String, User> users;
    HashSet<Event> events;

    public Organization(User admin) {
        this.admin = admin;
        this.events = new HashSet<>();
        this.users = new HashMap<>();
        users.put(this.admin.getID(), this.admin);
    }

    public User getUser(int ID) {
        return users.get(ID);
    }
}
