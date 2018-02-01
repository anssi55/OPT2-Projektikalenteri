package com.mycompany.projektikalenteri;
import java.sql.*;

/**
 *
 * @author anssi
 */
public class DatabaseHandler {
    
    public Connection Connect() throws SQLException {
        try(Connection conn = DriverManager.getConnection(
            "jdbc:mysql:10.114.34.77:3306/projektikalenteri", "anssi", "makkara2")) {
            System.out.println ("Database connection established");
            return conn;
        }
}
    public boolean addUser(String user,String password) {
        
        return false;
    }
}