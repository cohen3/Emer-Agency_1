package sample;

import sample.ModelLogic.DBHandler;

import java.util.HashMap;

public class AOrganizationListFactory {
    public static HashMap<String, Organization> generateList()
    {
        DBHandler db = DBHandler.getInstance();
        HashMap<String, Organization> h = new HashMap<>();
        h.put("Police", new Organization("Police"));
        h.put("Firefighters", new Organization("Firefighters"));
        h.put("Magen David Adom", new Organization("Magen David Adom"));
        h.put("agent", new Organization("agent"));
        return h;
    }
}
