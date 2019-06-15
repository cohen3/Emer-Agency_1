package sample;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Event {
    String eventID;
    String date;
    String header;
    String Status;
    String operator;
    ArrayList<Object> forces;
    ArrayList<Object> categories;

    LinkedList<Update> updates;
    LinkedList<User> securityPerson;

    public Event(String eventID, String date, String header, String status, String operator, ArrayList<Object> forces, ArrayList<Object> categories) {
        this.eventID = eventID;
        this.date = date;
        this.header = header;
        Status = status;
        this.operator = operator;
        updates = new LinkedList<>();
        securityPerson = new LinkedList<>();
        this.forces = forces;
        this.categories = categories;
    }

    public String getEventID(){return this.eventID;}

    public Update getLastUpdate() {
        return updates.getLast();
    }

    public void addUpdate(Update u) {
        updates.addLast(u);
    }

    public void addUpdates(LinkedList<Update> u) {
        updates = u;
    }

    public LinkedList<Update> getAllUpdates() {
        return updates;
    }

    public LinkedList<User> getAllSecurity() {
        return securityPerson;
    }

    public String getDate() {
        return date;
    }

    public String getHeader() {
        return header;
    }

    public String getStatus() {
        return Status;
    }

    public String getUser_ID() {
        return operator;
    }

    public LinkedList<Update> getSortedUpdates() {
        LinkedList<Update> u = updates;
        String new1 = u.get(0).date;
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(new1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        u.sort(new Comparator<Update>() {
            @Override
            public int compare(Update u1, Update u2) {
                return u2.getDate().compareTo(u1.getDate());
            }
        });
        Collections.reverse(u);
        return u;
    }


}
