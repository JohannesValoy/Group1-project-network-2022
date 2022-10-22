package no.ntnu.idata2304.group1.server.database;


public class SQLCommandFactory {


    private enum Tables {
        NODE("node"), ROOM("room"), TEMP("logs");

        private String table;

        Tables(String tableName) {
            this.table = tableName;
        }

        public String getTable() {
            return table;
        }
    }


    private SQLCommandFactory() {};


    public static String getTemperature(Integer roomID) throws IllegalArgumentException {
        return buildGetCommand(Tables.TEMP, "temp", "roomID", Integer.toString(roomID));
    }

    private static String buildGetCommand(Tables table, String toFetch) {
        return buildGetCommand(table, toFetch, "", "");
    }

    private static String buildGetCommand(Tables table, String toFetch, String columnToCheck,
            String value) {
        String sqlCommand = "SELECT " + toFetch + " FROM " + table.getTable();
        if (!(toFetch.isEmpty() || columnToCheck.isEmpty())) {
            sqlCommand += "WHERE " + columnToCheck + " LIKE " + value;
        }
        return sqlCommand;
    }
}
