package com.mycompany.projektikalenteri;
import java.sql.*;

/**
 *
 * @author anssi
 */
public class DatabaseHandler {
    
    public Connection connect() {
        try { Class.forName("com.mysql.jdbc.Driver");
        
        } catch (ClassNotFoundException e) {    
            System.err.println("JDBC-ajurin lataus epäonnistui");
            System.exit(-1);
        }
        try {
            Connection conn = DriverManager.getConnection(
            "jdbc:mysql:localhost:2222/projektikalenteri", "anssi", "makkara2");
            
            return conn;
    
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
}
    public boolean addUser(String user, String password) throws SQLException {
        
        int n = 0;
        try (Connection c = connect()) {
            PreparedStatement insertUser;
            String insertString = "INSERT INTO users (user, password) "
                    + "VALUES (?, ?)";
            c.setAutoCommit(false);
            insertUser = c.prepareStatement(insertString);
            insertUser.setString(0, user);
            insertUser.setString(1, password);
            n = insertUser.executeUpdate();
            c.commit();
        } 
        if(n == 1) {
            return true;
        }
            
        return false;
    }
}