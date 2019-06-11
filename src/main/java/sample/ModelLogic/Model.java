package sample.ModelLogic;

import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {
    private AccessLayer accessLayer =null;

    public Model(String path)
    {
        this.accessLayer = new AccessLayer();
        accessLayer.connectDB(path);
    }

    public RESULT AddEntry(ArrayList<Pair> fieldsNvalues, Tables table){return  accessLayer.AddEntry(fieldsNvalues,table);}
    public ArrayList<HashMap<String, String>> ReadEntries(ArrayList<Pair> fieldsNvalues, Tables table){ return accessLayer.ReadEntries(fieldsNvalues,table);}

    public RESULT UpdateEntries(Tables table, Fields fieldToUpdate, String newValue, ArrayList<Pair> fieldsNvalues){return accessLayer.UpdateEntries(table,fieldToUpdate,newValue,fieldsNvalues);}
    public RESULT DeleteEntry (Tables table, ArrayList<Pair> fieldValues){return accessLayer.DeleteEntry(table,fieldValues);}


}
