package sample;

import java.util.LinkedList;

public class Event {
    String date;
    String header;
    String Status;
    String operator;
    LinkedList<Update> updates;
    LinkedList<User> securityPerson;

    public Event(String date, String header, String status, String operator) {
        this.date = date;
        this.header = header;
        Status = status;
        this.operator = operator;
        updates = new LinkedList<>();
        securityPerson = new LinkedList<>();
    }

    public Update getLastUpdate()
    {
        return updates.getLast();
    }

    public void addUpdate(Update u)
    {
        updates.addLast(u);
    }

    public LinkedList<Update> getAllUpdates()
    {
        return updates;
    }

    public LinkedList<User> getAllSecurity()
    {
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
}
