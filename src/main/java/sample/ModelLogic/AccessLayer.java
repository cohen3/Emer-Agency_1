package sample.ModelLogic;

import javafx.util.Pair;
import sample.Enums.Fields;
import sample.Enums.RESULT;
import sample.Enums.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AccessLayer {

    private Connection connection;


    public void connectDB(String db_name){

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + db_name);
        }
        catch(Exception e) {
            System.out.println("could not establish a connection to database");
        }
    }

       public RESULT AddEntry(ArrayList<Pair> data, Tables table)
       {
            int size=data.size();
            if (size==0) {
                return RESULT.Fail;
            }
            StringBuilder paramsFields= new StringBuilder(" (");
            StringBuilder paramsFValues= new StringBuilder(" VALUES (");
            String qry = "INSERT INTO "+table;
            for(int i=0;i<size-1;i++){
                paramsFields.append("'").append(data.get(i).getKey()).append("', ");
                paramsFValues.append("?, ");
            }
            paramsFields.append("'").append(data.get(size - 1).getKey()).append("') ");
            paramsFValues.append("? )");
            qry=qry+paramsFields.toString()+paramsFValues.toString();
            try {
                PreparedStatement stmt = connection.prepareStatement(qry);
                for (int i = 1; i <=size ; i++) {
                    stmt.setString(i,(String)data.get(i-1).getValue());
                }
                stmt.execute();
            }catch (Exception e){
                //e.printStackTrace();
                return RESULT.Fail;
            }
            return RESULT.Success;
        }
    /**
     * This function reads entries from DB according the given parameters.
     * Actually it executes a SELECT query command.
     * The function selects the all columns of the given table
     * @param table- "FROM" which table to read
     * @param fieldsNvalues- list of fields and their values to put in "WHERE" condition
     * @return Table - the result of the query
     */
    public ArrayList<HashMap<String, String>> ReadEntries(ArrayList<Pair> fieldsNvalues, Tables table){

        ArrayList<HashMap<String, String>> ans = new ArrayList<>();
        StringBuilder fields = new StringBuilder();
        String sql= null;
        if(fieldsNvalues== null || fieldsNvalues.size()==0) {
            fieldsNvalues = new ArrayList<>();
            sql = "SELECT * FROM " + table;
        }else {
            for (Pair fieldsNvalue : fieldsNvalues) fields.append(fieldsNvalue.getKey()).append(" = ? and ");
                sql = "SELECT * FROM "+table+" WHERE "+fields;
                sql=sql.substring(0,sql.length()-5);
        }

//        sql=sql+";";

        try (PreparedStatement pstmt  = connection.prepareStatement(sql)){

            for (int i = 1; i <= fieldsNvalues.size(); i++)
                pstmt.setString(i, (String) fieldsNvalues.get(i-1).getValue());

            ResultSet result  = pstmt.executeQuery();
            ResultSetMetaData meta=result.getMetaData();
            int columnCount = meta.getColumnCount();

            while (result.next()){
                HashMap<String, String> map = new HashMap<String, String>();
                // The column count starts from 1
                for (int i = 1; i <= columnCount; i++ ) {
                    String name = meta.getColumnName(i);
                    map.put(name,result.getString(name));
                }
                ans.add(map);
            }


        } catch (SQLException e) {
            return null;
        }

        return ans;
    }

    /**
     * This function updates entries on DB according the given parameters
     * @param table which table to update
     * @param fieldToUpdate- given field to update
     * @param newValue- the new value to assign to given field
     * @param fieldsNvalues- list of fields and their values to put in "WHERE" condition
     * @return RESULT whether the update succeeded
     */
    public RESULT UpdateEntries(Tables table, Fields fieldToUpdate, String newValue, ArrayList<Pair> fieldsNvalues)
    {
        RESULT out=RESULT.Success;
        StringBuilder fields = new StringBuilder();
        String sql=null;
        if(fieldsNvalues== null || fieldsNvalues.size()==0) {
            fieldsNvalues = new ArrayList<>();
            sql = "UPDATE "+table+" SET "+fieldToUpdate+" = "+newValue;
        }else {
            for (Pair fieldsNvalue : fieldsNvalues) fields.append(fieldsNvalue.getKey()).append(" = ? and ");
            sql = "UPDATE " + table + " SET " + fieldToUpdate + " = " + newValue + " WHERE " + fields;
            sql = sql.substring(0,sql.length() - 5);
        }
        try (PreparedStatement pstmt  = connection.prepareStatement(sql)){

            for (int i = 1; i <= fieldsNvalues.size(); i++)
                pstmt.setString(i, (String) fieldsNvalues.get(i-1).getValue());

            int res=pstmt.executeUpdate();
            if(res==0)
                out = RESULT.Fail;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return out;
    }


    public RESULT DeleteEntry (Tables table, ArrayList<Pair> fieldValues)
    {
        int size= fieldValues.size();
        StringBuilder whereParam=new StringBuilder();
        for (int i = 0; i < size-1 ; i++) {
            whereParam.append(fieldValues.get(i).getKey()).append(" = ? and ");
        }
        whereParam.append(fieldValues.get(size - 1).getKey()).append(" = ? ;");
        String qry="DELETE FROM "+table+" WHERE " + whereParam.toString();
        try {
            PreparedStatement stmt = connection.prepareStatement(qry);
            for (int i = 1; i <=size ; i++) {
                stmt.setString(i,fieldValues.get(i-1).getValue().toString());
            }
            stmt.execute();
        }catch (Exception e){
            return RESULT.Fail;

        }
        return RESULT.Success;

    }

    public void discoonetDB(){
        try {
            connection.close();
        }catch (Exception e){
            System.out.println("could not establish connection");
        }
    }

    //    DDL FOR ListingVacation TABLE:
//    CREATE TABLE ListingVacation (
//            VacID         INT (16)    PRIMARY KEY,
//    airline       STRING (24),
//    FlightDate    STRING (16) NOT NULL,
//    Price         INT(8) NOT NULL,
//    ReturnDate    STRING (16),
//    Baggage       STRING (16),
//    adultTickets  INT (10),
//    childTickets  INT (10),
//    babyTickets   INT (10),
//    destination   STRING (24) NOT NULL,
//    includeReturn BOOLEAN     NOT NULL,
//    vacationType  STRING (24),
//    includeRoom   BOOLEAN,
//    placeRank     INT (3),
//    SellerID                  REFERENCES users (userName) ON DELETE CASCADE
//    ON UPDATE CASCADE,
//    Connection    BOOLEAN
//);

    // ADD RECORD TO ListingVacation query
//     INSERT INTO ListingVacation (VacID, airline, FlightDate, ReturnDate, Baggage, adultTickets, childTickets, babyTickets, destination, includeReturn, vacationType, includeRoom, placeRank)
//          VALUES (4,"Air1", "1/1/2091", "1/1/2092", "Hand", 5, 2, 2, "HAIFA", true, "idk", 1, 3);
//         INSERT INTO ListingVacation (VacID, airline, FlightDate, ReturnDate, Baggage, adultTickets, childTickets, babyTickets, destination, includeReturn, vacationType, includeRoom, placeRank)
//          VALUES (4,\"Air1\", \"1/1/2091\", \"1/1/2092\", \"Hand\", 5, 2, 2, \"HAIFA\", true, \"idk\", 1, 3);
}
