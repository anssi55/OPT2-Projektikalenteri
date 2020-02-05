package com.mycompany.projektikalenteri;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.scene.text.Text;
import javafx.scene.paint.Color;

/**
 *
 * @author anssi
 */
public class DatabaseHandler {
    private ResourceBundle resources;

    public DatabaseHandler(ResourceBundle resources) {
        this.resources = resources;
        try {

            try {
                Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                    | SecurityException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } catch (InstantiationException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        }

    }

    public Connection connect() {

        try {
            Connection conn = DriverManager
                    .getConnection("jdbc:mysql://localhost:2222/projektikalenteri?user=testi&password=testi");

            return conn;

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            System.out.print("StackTrace: ");
            ex.printStackTrace();

        }
        return null;
    }

    public boolean addUser(String user, String password, String email, String displayName) throws SQLException {

        int n = 0;
        try (Connection c = connect()) {
            PreparedStatement insertUser;
            String insertString = "INSERT INTO User (name, password, email, displayName) " + "VALUES (?, ?, ?, ?)";
            c.setAutoCommit(false);
            insertUser = c.prepareStatement(insertString);
            insertUser.setString(1, user);
            insertUser.setString(2, password);
            insertUser.setString(3, email);
            insertUser.setString(4, displayName);
            n = insertUser.executeUpdate();
            c.commit();
        }
        if (n == 1) {
            return true;
        }

        return false;
    }

    public boolean addUserEntry(Kayttaja user, String alku, String loppu, String data) throws SQLException {

        int n = 0;
        try (Connection c = connect()) {
            PreparedStatement insertEntry;
            String insertString = "INSERT INTO Entry (user_id, startingTime, endTime, data) " + "VALUES (?, ?, ?, ?)";

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
        if (n == 1) {
            return true;
        }

        return false;
    }

    public boolean addProjectEntry(Kayttaja user, Projekti project, String alku, String loppu, String data)
            throws SQLException {

        int n = 0;
        try (Connection c = connect()) {
            PreparedStatement insertEntry;
            String insertString = "INSERT INTO Entry (user_id, project_id, startingTime, endTime, data) "
                    + "VALUES (?, ?, ?, ?, ?)";

            insertEntry = c.prepareStatement(insertString);
            insertEntry.setString(1, "" + user.getId());
            insertEntry.setString(2, "" + project.getId());
            insertEntry.setString(3, alku);
            insertEntry.setString(4, loppu);
            insertEntry.setString(5, data);

            n = insertEntry.executeUpdate();

            PreparedStatement getEntry;
            String insertString2 = "SELECT id FROM Entry WHERE user_id = ? AND project_id = ? AND startingTime = ?"
                    + " AND endTime = ? AND data = ?";

            getEntry = c.prepareStatement(insertString2);
            getEntry.setString(1, "" + user.getId());
            getEntry.setString(2, "" + project.getId());
            getEntry.setString(3, alku);
            getEntry.setString(4, loppu);
            getEntry.setString(5, data);
            ResultSet rs = getEntry.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            Kalenterimerkinta m = new Kalenterimerkinta(id, user.getNayttonimi(), project.getNimi(), data);
            project.lisaaMerkinnat(m);

        }
        if (n == 1) {
            return true;
        }

        return false;
    }

    public boolean addProject(String name, Kayttaja k, Text t) throws SQLException {

        int n = 0;
        try (Connection c = connect()) {
            PreparedStatement checkProject;
            String insertString3 = "SELECT id from Project " + "WHERE name = ?  AND boss = ?";

            checkProject = c.prepareStatement(insertString3);
            checkProject.setString(1, name);
            checkProject.setInt(2, k.getId());
            ResultSet rs = checkProject.executeQuery();

            if (rs.next()) {
                t.setFill(Color.RED);
                t.setText(resources.getString("nameInUse"));
                return false;
            }

            PreparedStatement insertProject;
            String insertString = "INSERT INTO Project (name, boss) " + "VALUES (?, ?)";

            insertProject = c.prepareStatement(insertString);
            insertProject.setString(1, name);
            insertProject.setInt(2, k.getId());
            n = insertProject.executeUpdate();

            PreparedStatement getProject;
            String insertString2 = "SELECT id from Project " + "WHERE name = ?  AND boss = ?";

            getProject = c.prepareStatement(insertString2);
            getProject.setString(1, name);
            getProject.setInt(2, k.getId());
            ResultSet r = getProject.executeQuery();
            r.next();
            int p = r.getInt("id");
            Projekti pr = new Projekti(p, name, k.getNayttonimi());
            k.lisaaPomona(pr);
        }
        if (n == 1) {
            return true;
        }

        return false;
    }

    public Kayttaja loadUser(String username, String passw, Text t) throws SQLException {
        Connection c;
        ResultSet rs;

        c = connect();
        if (c == null) {
            t.setText(resources.getString("noConnection"));
        }
        PreparedStatement insertUser;
        String insertString = "SELECT * from User " + "WHERE name = ?";
        insertUser = c.prepareStatement(insertString);
        insertUser.setString(1, username);
        rs = insertUser.executeQuery();
        if (rs.next()) {

            int id = rs.getInt("id");
            String dname = rs.getString("displayName");
            String email = rs.getString("email");
            String password = rs.getString("password");
            if (!password.equals(passw)) {
                System.out.println("Väärä salasana");
                return null;
            }
            System.out.println("Salasana oikein");
            Kayttaja k = new Kayttaja(id, email, dname);
            loadProjects(k, id, c);
            return k;
        } else {
            t.setText("Käyttäjää ei löytynyt!");
            t.setFill(Color.RED);
        }
        return null;

    }

    public void loadProjects(Kayttaja k, int id, Connection c) throws SQLException {

        ResultSet rs;

        PreparedStatement insertUser;
        String insertString = "SELECT * from Project " + "WHERE boss = ?";
        c.setAutoCommit(false);
        insertUser = c.prepareStatement(insertString);
        insertUser.setInt(1, id);

        rs = insertUser.executeQuery();
        c.commit();

        while (rs.next()) {
            int pid = rs.getInt("id");
            String name = rs.getString("name");
            int boss = rs.getInt("boss");
            String bossS = getNickname(boss, c);
            Projekti p = new Projekti(pid, name, bossS);
            loadProjectEntrys(p, pid, c);
            k.lisaaPomona(p);
        }

        PreparedStatement insertUser2;
        String insertString2 = "SELECT * from ProjectUser " + "WHERE user_id  = ?";
        c.setAutoCommit(false);
        insertUser2 = c.prepareStatement(insertString2);
        insertUser2.setInt(1, id);

        rs = insertUser2.executeQuery();
        c.commit();

        while (rs.next()) {
            int pid = rs.getInt("id");
            String name = rs.getString("name");
            int boss = rs.getInt("boss");
            String bossS = getNickname(boss, c);
            Projekti p = new Projekti(id, name, bossS);
            loadProjectEntrys(p, pid, c);
            k.lisaaTekijana(p);
        }
    }

    public void loadProjectEntrys(Projekti p, int pid2, Connection c) throws SQLException {
        ResultSet rs = null;

        PreparedStatement insertUser;
        String insertString = "SELECT * from Entry " + "WHERE project_id = ?";

        c.setAutoCommit(false);
        insertUser = c.prepareStatement(insertString);
        insertUser.setInt(1, pid2);

        rs = insertUser.executeQuery();
        c.commit();

        while (rs.next()) {
            int pid = rs.getInt("id");
            int userId = rs.getInt("user_id");
            String username = getNickname(userId, c);
            String sdate = rs.getString("startingTime");
            String edate = rs.getString("endTime");
            String text = rs.getString("data");
            Kalenterimerkinta ka = new Kalenterimerkinta(pid, username, p.getNimi(), text);
            ka.setAika(sdate, edate);
            p.lisaaMerkinnat(ka);

        }
    }

    public String getNickname(int id, Connection c) {
        ResultSet rs = null;
        String nickName;
        try {
            PreparedStatement insertUser;
            String insertString = "SELECT displayName from User " + "WHERE id = ?";
            c.setAutoCommit(false);
            insertUser = c.prepareStatement(insertString);
            insertUser.setInt(1, id);

            rs = insertUser.executeQuery();
            c.commit();
            while (rs.next()) {
                nickName = rs.getString("displayName");
                return nickName;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    public void loadEntrys(Kayttaja k, String id, Connection c) throws SQLException {
        ResultSet rs = null;

        PreparedStatement insertUser;
        String insertString = "SELECT * from Entry " + "WHERE user_id = ?";

        c.setAutoCommit(false);
        insertUser = c.prepareStatement(insertString);
        insertUser.setString(1, id);

        rs = insertUser.executeQuery();
        c.commit();

        while (rs.next()) {
            int pid = rs.getInt("id");
            String username = rs.getString("user_id");
            String sdate = rs.getString("startingTime");
            String edate = rs.getString("endTime");
            String text = rs.getString("data");
            Kalenterimerkinta ka = new Kalenterimerkinta(pid, k.getNayttonimi(), text);
            ka.setAika(sdate, edate);
            k.lisaaMerkinnat(ka);

        }
    }

    public boolean removeProject(String name, Kayttaja k, Text t) throws SQLException {
        int n = 0;
        try (Connection c = connect()) {
            PreparedStatement checkProject;
            String insertString3 = "DELETE from Project " + "WHERE name = ?  AND boss = ?";

            checkProject = c.prepareStatement(insertString3);
            checkProject.setString(1, name);
            checkProject.setInt(2, k.getId());
            n = checkProject.executeUpdate();
            k.removeProjectFromList(name);
            if (n == 0) {
                t.setFill(Color.RED);
                t.setText(resources.getString("coudlntRemove"));
                return false;
            }
        }
        return true;
    }

    public void changeResourceBundle(ResourceBundle b) {
        this.resources = b;

    }
}