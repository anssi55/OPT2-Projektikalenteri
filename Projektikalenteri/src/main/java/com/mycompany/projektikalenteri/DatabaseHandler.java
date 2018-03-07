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
            "jdbc:mysql://80.220.69.109:3306/projektikalenteri?user=root&password=kakka123");
            
            return conn;
    
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            System.out.print("StackTrace: ");
            ex.printStackTrace();
            System.exit(0);
        }
        return null;
}
    
    public boolean addUser(String user, String password, String email, String displayName) throws SQLException {
        
        int n = 0;
        try (Connection c = connect()) {
            PreparedStatement insertUser;
            String insertString = "INSERT INTO User (name, password, email, displayName) "
                    + "VALUES (?, ?, ?, ?)";
            c.setAutoCommit(false);
            insertUser = c.prepareStatement(insertString);
            insertUser.setString(1, user);
            insertUser.setString(2, password);
            insertUser.setString(3, email);
            insertUser.setString(4, displayName);
            n = insertUser.executeUpdate();
            c.commit();
        } 
        if(n == 1) {
            return true;
        }
            
        return false;
    }
    public boolean addUserEntry(Kayttaja user, String alku, String loppu, String data) throws SQLException {
        
        int n = 0;
        try (Connection c = connect()) {
            PreparedStatement insertEntry;
            String insertString = "INSERT INTO Entry (user_id, startingTime, endTime, data) "
                    + "VALUES (?, ?, ?, ?)";
            
            insertEntry = c.prepareStatement(insertString);
            insertEntry.setString(1, "" + user.getId());
            insertEntry.setString(2, alku);
            insertEntry.setString(3, loppu);
            insertEntry.setString(4, data);
            n = insertEntry.executeUpdate();
            
            PreparedStatement getEntry;
            String insertString2 = "SELECT id FROM Entry WHERE user_id = ? AND startingTime = ?"
            		+ " AND endTime = ? AND data = ?";
                    
            
            getEntry = c.prepareStatement(insertString2);
            getEntry.setString(1, "" + user.getId());
            getEntry.setString(2, alku);
            getEntry.setString(3, loppu);
            getEntry.setString(4, data);
            ResultSet rs = getEntry.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            Kalenterimerkinta m = new Kalenterimerkinta(id, user.getNayttonimi(), data);
            user.lisaaMerkinnat(m);
            
        } 
        if(n == 1) {
            return true;
        }
            
        return false;
    }
    public boolean addProjectEntry(Kayttaja user, Projekti project, String alku, String loppu, String data) throws SQLException {
        
        int n = 0;
        try (Connection c = connect()) {
            PreparedStatement insertEntry;
            String insertString = "INSERT INTO Entry (user_id, project_id, startingTime, endTime, data) "
                    + "VALUES (?, ?, ?, ?, ?)";
            
            insertEntry = c.prepareStatement(insertString);
            insertEntry.setString(1, "" + user.getId());
            insertEntry.setString(2, "" +project.getId());
            insertEntry.setString(3, alku);
            insertEntry.setString(4, loppu);
            insertEntry.setString(5, data);
            
            n = insertEntry.executeUpdate();
            
            PreparedStatement getEntry;
            String insertString2 = "SELECT id FROM Entry WHERE user_id = ? AND project_id = ? AND startingTime = ?"
            		+ " AND endTime = ? AND data = ?";
                    
            
            getEntry = c.prepareStatement(insertString2);
            getEntry.setString(1, ""+ user.getId());
            getEntry.setString(2, ""+project.getId());
            getEntry.setString(3, alku);
            getEntry.setString(4, loppu);
            getEntry.setString(5, data);
            ResultSet rs = getEntry.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            Kalenterimerkinta m = new Kalenterimerkinta(id, user.getNayttonimi(), project.getNimi(), data);
            project.lisaaMerkinnat(m);
            
        } 
        if(n == 1) {
            return true;
        }
            
        return false;
    }
    public boolean addProject(String name, Kayttaja k) throws SQLException {
        
        int n = 0;
        try (Connection c = connect()) {
        	PreparedStatement checkProject;
            String insertString3 = "SELECT id from Project "
                    + "WHERE name = ?  AND boss = ?";
            
            checkProject = c.prepareStatement(insertString3);
            checkProject.setString(1, name);
            checkProject.setInt(2, k.getId());
            ResultSet rs = checkProject.executeQuery();
            rs.next();
            if (rs.wasNull()) {
            	return false;
            }
        	
            PreparedStatement insertProject;
            String insertString = "INSERT INTO Project (name, boss) "
                    + "VALUES (?, ?)";
            
            insertProject = c.prepareStatement(insertString);
            insertProject.setString(1, name);
            insertProject.setInt(2, k.getId());
            n = insertProject.executeUpdate();
            
            PreparedStatement getProject;
            String insertString2 = "SELECT id from Project "
                    + "WHERE name = ?  AND boss = ?";
            
            getProject = c.prepareStatement(insertString2);
            getProject.setString(1, name);
            getProject.setInt(2, k.getId());
            ResultSet r = getProject.executeQuery();
            r.next();
            int p =  r.getInt("id");
            Projekti pr = new Projekti(p, name, k.getNayttonimi());
            k.lisaaPomona(pr);
        } 
        if(n == 1) {
            return true;
        }
            
        return false;
    }
    public Kayttaja loadUser(String username, String passw) throws SQLException {
    	Connection c;
    	ResultSet rs;
        
    	c = connect();
        PreparedStatement insertUser;
        String insertString = "SELECT * from User "
                + "WHERE name = ?";
        insertUser = c.prepareStatement(insertString);
        insertUser.setString(1, username);
        rs = insertUser.executeQuery();
        rs.next();
        

        int id= rs.getInt("id");
        String dname = rs.getString("displayName");
        String email = rs.getString("email");
        String password = rs.getString("password");
        if(!password.equals(passw)) {
        	System.out.println("Väärä salasana");
        	return null;
        }
        System.out.println("Salasana oikein");
        Kayttaja k = new Kayttaja(id, email, dname);
        loadProjects(k, id, c);

		return k;
    	
    }
    public void loadProjects(Kayttaja k, int id, Connection c) throws SQLException {
    	
    	ResultSet rs;
        
        PreparedStatement insertUser;
        String insertString = "SELECT * from Project "
                + "WHERE boss = ?";
        c.setAutoCommit(false);
        insertUser = c.prepareStatement(insertString);
        insertUser.setInt(1, id);
            
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
        insertUser2.setInt(1, id);
        
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
            insertUser.setString(1, id);
            
            rs = insertUser.executeQuery();
            c.commit();
        
        while(rs.next()) {
	        int pid= rs.getInt("id");
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
            insertUser.setString(1, id);
            
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
            insertUser.setString(1, id);
            
            rs = insertUser.executeQuery();
            c.commit();
			
        while(rs.next()) {
	        int pid= rs.getInt("id");
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