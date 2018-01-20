/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database ;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseConnection {

    String db = "AdvancedRecommendation1";
    String username = "root";
    String password = "root";
    Connection con = null;
    Statement stmt;
    ResultSet rs = null;
    public DatabaseConnection() {
    	
    }

    public Connection dbconnection() {

        try {
            String url = "jdbc:mysql://localhost/" + db;
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            con =DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        return con;
    }

    public ResultSet getResultSet(String query) {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public int getUpdate(String query) {
        int i = 0;
        try {
            stmt = con.createStatement();
            i = stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }  
    
}
