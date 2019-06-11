import java.util.LinkedList;

public class Event {
    String date;
    String header;
    String Status;
    int user_ID;
    LinkedList<Update> updates;
    LinkedList<User> securityPerson;

    public Event(String date, String header, String status, int user_ID) {
        this.date = date;
        this.header = header;
        Status = status;
        this.user_ID = user_ID;
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

    public int getUser_ID() {
        return user_ID;
    }
}
