package com.mycompany.projektikalenteri;
import java.sql.*;

/**
 *
 * @author anssi
 */
public class DatabaseHandler {
	public DatabaseHandler() {
		try {
		
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    public Connection connect() {
        
        try {
            Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:2222/projektikalenteri?user=anssi&password=makkara2");
            
            return conn;
    
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            System.exit(0);
        }
        return null;
}
    
    public boolean addUser(String user, String password) throws SQLException {
        
        int n = 0;
        try (Connection c = connect()) {
            PreparedStatement insertUser;
            String insertString = "INSERT INTO User (user, password) "
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
    public Kayttaja loadUser(String email) throws SQLException {
    	Kayttaja n = null;
        try (Connection c = connect()) {
            PreparedStatement insertUser;
            String insertString = "SELECT * from users "
                    + "WHERE email = ?";
            c.setAutoCommit(false);
            insertUser = c.prepareStatement(insertString);
            insertUser.setString(0, email);
            
            int rs = insertUser.executeUpdate();
            c.commit();
        } 
        if(n == null) {
            return null;
        }
            
        
		return null;
    	
    }
}