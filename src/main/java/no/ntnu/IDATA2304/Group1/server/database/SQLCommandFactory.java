package no.ntnu.idata2304.group1.server.database;

import org.json.JSONArray;
import org.json.JSONObject;

public class SQLCommandFactory {

    /**
     * Supported Commands to SQL
     */
    public static enum Command {
        GET(0);
        private int id;
        Command(int id){
            this.id = id;
        };
        public int getId(){
            return id;
        }
    }


    private SQLCommandFactory() {};
    //TODO: Translate a JSONObject over to a SQL Command
    public static String createSQLCommand(JSONObject jsonObject){
        String string;
        throw new RuntimeException("Not Implemented"); 
    }

    //TODO: Translate the GET command to SQL and fetch the response
    private static String getSQL(JSONObject object) {
        try {
            JSONArray rooms = object.getJSONArray("room");

        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }


}
