package fr.mouton_redstone.myeasyspawn.models;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.logging.Level;

public class SQLInterface {
    private static Plugin plugin = MyEasySpawn.getPlugin();
    private File sqlFile;
    private Connection conn;
    private Statement statement;
    private HashMap<Integer, HashMap<String, Object>> rows = new HashMap();
    private int numRows = 0;

    public SQLInterface(Plugin plugin) {
        File directory = new File(MyEasySpawn.getPlugin().getDataFolder().getAbsolutePath());
        if (!directory.exists()) {
            directory.mkdir();
        }

        this.sqlFile = new File(directory + File.separator + "MyEasySpawnDatabase.db");
    }

    //SQL database connection related
    public synchronized Connection getConnection() { //get SQL database
        try {
            if (this.conn == null || this.conn.isClosed()) { // if no connection to database, create new one
                return this.openConnection();
            }
        } catch (Exception e) {
            plugin.getLogger().severe(e.getMessage());
        }

        return this.conn; // else, return current connection
    }

    public synchronized Connection openConnection() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + this.sqlFile.getAbsolutePath());
            return this.conn;
        } catch (Exception e) {
            plugin.getLogger().severe(e.getMessage());
            return null;
        }
    }

    public synchronized void closeConnection() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (Exception e) {
                plugin.getLogger().severe(e.getMessage());
            }
        }
    }

    //Actual Queries
    public boolean checkTable(String tableName) { // returns true if table exists
        try {
            DatabaseMetaData dbm = this.getConnection().getMetaData();
            ResultSet tables = dbm.getTables((String)null, (String)null, tableName, (String[])null);
            if (tables.next()) {
                tables.close();
                return true;
            } else {
                tables.close();
                return false;
            }
        } catch (Exception e) {
            plugin.getLogger().severe(e.getMessage());
            return false;
        }
    }

    public boolean createTable(String tableName, String[] columns, String[] dims) {
        try {
            this.statement = this.getConnection().createStatement();
            String query = "CREATE TABLE " + tableName + "(";

            for(int i = 0; i < columns.length; ++i) {
                if (i != 0) {
                    query = query + ",";
                }

                query = query + columns[i] + " " + dims[i];
            }

            query = query + ")";
            this.statement.execute(query);
        } catch (Exception e) {
            plugin.getLogger().severe(e.getMessage());
        }

        return true;
    }

    public ResultSet query(String query) {
        ResultSet results = null;

        try {
            this.statement = this.getConnection().createStatement();
            results = this.statement.executeQuery(query);
            return results;
        }
        catch (Exception e) {
            plugin.getLogger().severe(e.getMessage());
            return null;
        }
    }

    public HashMap<Integer, HashMap<String, Object>> select(String fields, String tableName, String where, String group, String order) {
        if ("".equals(fields) || fields == null) {fields = "*";}

        String query = "SELECT " + fields + " FROM " + tableName;
        if (!"".equals(where) && where != null) {query = query + " WHERE " + where;}

        if (!"".equals(group) && group != null) {query = query + " GROUP BY " + group;}

        if (!"".equals(order) && order != null) {
            query = query + " ORDER BY " + order;
        }

        ResultSet results = null;

        try {
            this.rows.clear();
            this.numRows = 0;
            results = this.query(query);
            if (results == null) {
                return null;
            } else {
                int columnsCount = results.getMetaData().getColumnCount();
                String columnNames = "";

                for(int i = 1; i <= columnsCount; ++i) {
                    if (!"".equals(columnNames)) {
                        columnNames = columnNames + ",";
                    }

                    columnNames = columnNames + results.getMetaData().getColumnName(i);
                }

                String[] columnArray = columnNames.split(",");

                for(this.numRows = 0; results.next(); ++this.numRows) {
                    HashMap<String, Object> thisColumn = new HashMap();
                    String[] columns = columnArray;
                    int columnLength = columnArray.length;

                    for(int i = 0; i < columnLength; ++i) {
                        String columnName = columns[i];
                        thisColumn.put(columnName, results.getObject(columnName));
                    }

                    this.rows.put(this.numRows, thisColumn);
                }

                results.close();
                this.statement.close();
                return this.rows;
            }
        } catch (Exception exception) {
            plugin.getLogger().severe(exception.getMessage());
            if (results != null) {
                try {
                    results.close();
                    this.statement.close();
                } catch (Exception e) {
                }
            }

            return null;
        }
    }

    public boolean tableContainsColumn(String tableName, String columnName) {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                this.openConnection();
            }

            this.statement = this.conn.createStatement();
            ResultSet rs = this.statement.executeQuery("SELECT `" + columnName + "` FROM `" + tableName + "` LIMIT 1");
            if (rs == null) {
                return false;
            }

            this.closeConnection();
        } catch (SQLException e) {
            plugin.getLogger().severe(e.getMessage());
        }

        return true;
    }

    public void addColumn(String tableName, String columnDef) {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                this.openConnection();
            }

            this.statement = this.conn.createStatement();
            this.statement.executeUpdate("ALTER TABLE `" + tableName + "` ADD COLUMN `" + columnDef + "`");
            this.statement.close();
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Error adding column to ''{0}''!", tableName);
            e.printStackTrace();
        }
    }

    public static Location resultToLocation(ResultSet result){
        try{
            World world = plugin.getServer().getWorld(result.getString("world"));
            Double x = result.getDouble("x");
            Double y = result.getDouble("y");
            Double z = result.getDouble("z");
            Location loc = new Location(world, x, y, z);
            return loc;
        }catch(SQLException e){
            plugin.getLogger().severe(e.getMessage());
           return null;
        }
    }
}
