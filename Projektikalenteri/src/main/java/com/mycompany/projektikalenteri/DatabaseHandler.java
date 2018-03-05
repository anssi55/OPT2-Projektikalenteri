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
    public boolean addProject(String name, Kayttaja k) throws SQLException {
        
        int n = 0;
        try (Connection c = connect()) {
            PreparedStatement insertUser;
            String insertString = "INSERT INTO Project (name, boss) "
                    + "VALUES (?, ?)";
            c.setAutoCommit(false);
            insertUser = c.prepareStatement(insertString);
            insertUser.setString(0, name);
            insertUser.setString(1, k.getId());
            n = insertUser.executeUpdate();
            c.commit();
        } 
        if(n == 1) {
            return true;
        }
            
        return false;
    }
    public Kayttaja loadUser(String email) throws SQLException {
    	Connection c;
    	ResultSet rs;
        
    	c = connect();
        PreparedStatement insertUser;
        String insertString = "SELECT * from Users "
                + "WHERE email = ?";
        c.setAutoCommit(false);
        insertUser = c.prepareStatement(insertString);
        insertUser.setString(0, email);
        
        rs = insertUser.executeQuery();
        c.commit();
        
        String id= rs.getString("id");
        String dname = rs.getString("displayName");
        String name = rs.getString("name");
        Kayttaja k = new Kayttaja(id, email, dname);
        loadProjects(k, id, c);
        
        
		return k;
    	
    }
    public void loadProjects(Kayttaja k, String id, Connection c) throws SQLException {
    	
    	ResultSet rs;
        
            PreparedStatement insertUser;
            String insertString = "SELECT * from Project "
                    + "WHERE boss = ?";
            c.setAutoCommit(false);
            insertUser = c.prepareStatement(insertString);
            insertUser.setString(0, id);
            
            rs = insertUser.executeQuery();
            c.commit();
        
        while(rs.next()) {
	        String pid= rs.getString("id");
	        String name = rs.getString("name");
	        String boss = rs.getString("boss");
	        boss = getNickname(boss, c);
	        Projekti p = new Projekti(id, name, boss);
	        loadProjectEntrys(p, pid, c);
	        k.lisaaPomona(p);
        }
        
        
        PreparedStatement insertUser2;
        String insertString2 = "SELECT * from ProjectUser "
                + "WHERE user_id  = ?";
        c.setAutoCommit(false);
        insertUser2 = c.prepareStatement(insertString2);
        insertUser2.setString(0, id);
        
        rs = insertUser2.executeQuery();
        c.commit();
    
	    while(rs.next()) {
	        String pid= rs.getString("id");
	        String name = rs.getString("name");
	        String boss = rs.getString("boss");
	        boss = getNickname(boss, c);
	        Projekti p = new Projekti(id, name, boss);
	        loadProjectEntrys(p, pid, c);
	        k.lisaaTekijana(p);
	    }
    }
    public void loadProjectEntrys(Projekti p, String id, Connection c) throws SQLException {
    	ResultSet rs = null;
        
        PreparedStatement insertUser;
        String insertString = "SELECT * from Entry "
                + "WHERE project_id = ?";
        
			c.setAutoCommit(false);
			insertUser = c.prepareStatement(insertString);
            insertUser.setString(0, id);
            
            rs = insertUser.executeQuery();
            c.commit();
			
				
			
            
        
        while(rs.next()) {
	        String pid= rs.getString("id");
	        String username = rs.getString("user_id");
	        username = getNickname(username, c);
	        String sdate = rs.getString("startingTime");
	        String edate = rs.getString("endTime");
	        String text = rs.getString("data");
	        Kalenterimerkinta ka = new Kalenterimerkinta(pid, username, p.getNimi(), text);
	        ka.setAika(sdate, edate);
	        p.lisaaMerkinnat(ka);
        
        }
    }
    public String getNickname(String id, Connection c) {
    	ResultSet rs = null;
    	String pid = null;
        try {
            PreparedStatement insertUser;
            String insertString = "SELECT displayName from User "
                    + "WHERE id = ?";
            c.setAutoCommit(false);
            insertUser = c.prepareStatement(insertString);
            insertUser.setString(0, id);
            
            rs = insertUser.executeQuery();
            c.commit();
            pid = rs.getString("id");
        
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return pid;
    }
    public void loadEntrys(Kayttaja k, String id, Connection c) throws SQLException {
    	ResultSet rs = null;
        
        PreparedStatement insertUser;
        String insertString = "SELECT * from Entry "
                + "WHERE user_id = ?";
        
			c.setAutoCommit(false);
			insertUser = c.prepareStatement(insertString);
            insertUser.setString(0, id);
            
            rs = insertUser.executeQuery();
            c.commit();
			
        while(rs.next()) {
	        String pid= rs.getString("id");
	        String username = rs.getString("user_id");
	        String sdate = rs.getString("startingTime");
	        String edate = rs.getString("endTime");
	        String text = rs.getString("data");
	        Kalenterimerkinta ka = new Kalenterimerkinta(pid, k.getNayttonimi(), text);
	        ka.setAika(sdate, edate);
	        k.lisaaMerkinnat(ka);
        
        }
   }
}

    

