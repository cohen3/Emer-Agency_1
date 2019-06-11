package sample;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Update {
    String Information;
    String date;

    public Update(String information, String date) {
        Information = information;
        this.date = date;
    }

    public Update() {
        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    public String getInformation() {
        return Information;
    }

    public String getDate() {
        return date;
    }

    public void Edit(String information) {
        Information = information;
    }

    @Override
    public String toString() {
        return String.format("information = " +Information+ ", date = " + date);
    }
}
