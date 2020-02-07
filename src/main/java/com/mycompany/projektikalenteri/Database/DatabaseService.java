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
public class DatabaseService {
    private ResourceBundle resources;
    static Connection connection = null;
    static PreparedStatement preparedStatement = null;

    public DatabaseService(ResourceBundle resources) {
        this.resources = resources;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void connect() {

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:2222/projektikalenteri?user=testi&password=testi");

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    public LoggedUser createUser(String userName, String password, String displayName) throws Exception {
        int i;
        try {
            String insertString = "INSERT INTO person (user_name, password, display_name) " + "VALUES (?, ?, ?)";
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, passwordService.CryptPassword(password));
            preparedStatement.setString(3, displayName);
            preparedStatement.executeUpdate();
            connection.commit();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt("id");
            }
            LoggedUser user = new LoggedUser(id, userName, displayName);
            return user;

        } catch (SQLException e) {
            throw new Exception(resources.getString("databaseError"));
        }

    }

    public boolean createCalendarEntry(Kayttaja user, Projekti project, LocalDate startDate, LocalDate endDate,
            String entry) throws SQLException {

        int id;
        int projectId = null;
        try {
            if (project) {
                projectId = "" + project.getId();
            }
            String insertString = "INSERT INTO Entry (id, project_id, start_time, end_time, entry) "
                    + "VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, projectId);
            preparedStatement.setString(3, startDate);
            preparedStatement.setString(4, endDate);
            preparedStatement.setString(5, entry);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt("id");
            }
            if (project) {
                Kalenterimerkinta calendarEntry = new Kalenterimerkinta(id, project, entry);
            } else {
                Kalenterimerkinta calendarEntry = new Kalenterimerkinta(id, entry);
            }

            user.lisaaMerkinnat(calendarEntry);

        } catch (SQLException e) {

            e.printStackTrace();

        }
        if (success == 1) {
            return true;
        }

        return false;
    }

    public Project createProject(String name, User user) throws Exception {
        int id;
        ResultSet rs;
        try {

            String queryString = "SELECT id from project " + "WHERE name = ?  AND person_id = ?";

            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, user.getId());
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                throw new Exception(resources.getString("nameInUse"));
            }

            String insertString = "INSERT INTO project (name, person_id) " + "VALUES (?, ?)";

            preparedStatement = c.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, user.getId());
            insertProject.executeUpdate();
            rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            Project project = new Project(id, name, groupLeader);
            return project;

        } catch (SQLException e) {
            throw new Exception(resources.getString("databaseError"));
        }

    }

    public LoggedInUser loadLoggedInUser(String userName, String passW) throws Exception {
        LoggedUser user;
        String insertString = "SELECT * FROM person " + "WHERE user_name = ?";
        preparedStatement = connection.prepareStatement(insertString);
        preparedStatement.setString(1, userName);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {

            int userId = rs.getInt("id");
            String displayName = rs.getString("display_name");
            String email = rs.getString("user_name");
            String password = rs.getString("password");
            passwordService.checkPassword(password, passW);
            user = new LoggedUser(id, email, dname);
            loadProjects(user, userId);
            return user;
        } else {
            throw new Exception(resources.getString("databaseError"));
        }
        return user;

    }

    public User loadUser(int userId) throws Exception {
        User user;
        String insertString = "SELECT user_name, display_name from person " + "WHERE user_id = ?";
        preparedStatement = connection.prepareStatement(insertString);
        preparedStatement.setInt(1, userId);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            String displayName = rs.getString("display_name");
            String userName = rs.getString("user_name");
            user = new User(userId, userName, displayName);
        } else {
            throw new Exception(resources.getString("databaseError"));
        }
        return user;

    }

    public void loadProjects(LoggerUser user, int userId) throws Exception {

        String queryProjectWhereLeaderString = "SELECT * from project " + "WHERE user_id = ?";
        connection.setAutoCommit(false);
        insertUser = connection.prepareStatement(queryProjectWhereLeaderString);
        insertUser.setInt(1, userID);

        ResultSet rs = insertUser.executeQuery();
        connection.commit();

        while (rs.next()) {
            int id = rs.getInt("project_id");
            String name = rs.getString("name");
            int groupLeader = rs.getInt("user_id");
            Project project = new Project(id, name);
            loadProjectEntrys(project, id);
            user.setProjectWhereLeader(project);
        }

        String queryProjectWhereMemberString = "SELECT project_member.project_id, project.person_id, project.name FROM project_members "
                + "INNER JOIN project ON project_member.person_id = project.person_id WHERE person_id = ?";
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(queryProjectWhereMemberString);
        preparedStatement.setInt(1, userID);

        rs = insertUser2.executeQuery();
        c.commit();

        while (rs.next()) {
            int projectMemberId = rs.getInt("project_members_id");
            String name = rs.getString("name");
            int projectLeaderId = rs.getInt("person_id");
            User projectLeader = loadUser(projectLeaderId);
            Project project = new Project(id, name, projectLeader);
            loadProjectEntries(project);
            user.setProjectWhereLeader(project);
        }
    }

    public void loadProjectEntries(Project project) throws Exception {
        try {
            String queryString = "SELECT * from project_entry WHERE project_id = ?";

            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setInt(1, project.getId());

            ResultSet rs = insertUser.executeQuery();
            connection.commit();

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                User user = project.getTeamMember(userId);
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String entry = rs.getString("entry");
                ProjectCalendarEntry projectCalendarEntry = new ProjectCalendarEntry(id, entry, startTime, endTime,
                        user);
                projectCalendarEntry.setAika(startTime, endTime);
                project.setEntry(projectCalendarEntry);
            }
        } catch (Exception e) {
            throw new Exception(resources.getString("databaseError"));
        }
    }

    public void loadProjectMembers(Project project) {
        try {
            String insertString = "SELECT project_member.person_id, person.user_name, person.display_name FROM project_members "
                    + "INNER JOIN person ON project_member.person_id = person.id WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setInt(1, project.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("person_id");
                String userName = rs.getString("user_name");
                String displayName = rs.getString("display_name");
                User teamMember = new User(userId, userName, displayName);
                project.setTeamMember(teamMember);
            }
        } catch (Exception e) {
            throw new Exception(resources.getString("databaseError"));
        }
    }

    public void loadEntries(LoggedInUser user) throws Exception {

        try {
            String queryString = "SELECT * from Entry " + "WHERE user_id = ?";

            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setString(1, id);

            rs = preparedStatement.executeQuery();
            connection.commit();

            while (rs.next()) {
                int id = rs.getInt("id");
                String startDate = rs.getString("start_time");
                String endDate = rs.getString("end_time");
                String entry = rs.getString("entry");
                CalendarEntry calendarEntry = new CalendarEntry(id, entry, startDate, endDate);
                user.setEntry(calendarEntry);

            }
        } catch (Exception e) {
            throw new Exception(resources.getString("databaseError"));
        }
    }

    public void deleteProject(int projectId) throws Exception {

        try {

            String deleteString = "DELETE from project WHERE id = ?";

            preparedStatement = connection.prepareStatement(deleteString);
            preparedStatement.setInt(1, projectId);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new Exception(resources.getString("databaseError"));
        }

    }

}