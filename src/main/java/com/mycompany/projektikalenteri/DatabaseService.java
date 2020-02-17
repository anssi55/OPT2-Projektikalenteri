package com.mycompany.projektikalenteri;

import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

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


    public LoggedInUser createUser(String userName, String password, String displayName) throws Exception {
        int id = -1;
        try {
            String query = "INSERT INTO person (user_name, password, display_name) " + "VALUES (?, ?, ?)";
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, PasswordService.generateSecurePassword(password));
            preparedStatement.setString(3, displayName);
            preparedStatement.executeUpdate();
            connection.commit();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt("id");
            } else {
            	throw new Exception(resources.getString("databaseError"));
            }
            LoggedInUser user = new LoggedInUser(id, userName, displayName);
            return user;

        } catch (SQLException e) {
            throw new Exception(resources.getString("databaseError"));
        }

    }
    public CalendarEntry createCalendarEntry(LoggedInUser user, String startTime, String endTime,
			String entry) throws Exception {

        int id = -1;
      try {
       
            String query = "INSERT INTO Entry (person_id, start_time, end_time, entry) "
                    + "VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, startTime);
            preparedStatement.setString(3, endTime);
            preparedStatement.setString(4, entry);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt("id");
            } else {
            	throw new Exception(resources.getString("databaseError"));
            }
          
           CalendarEntry calendarEntry = new CalendarEntry(id, entry, LocalDate.parse(startTime), LocalDate.parse(endTime));      

            return calendarEntry;

        } catch (SQLException e) {

        	throw new Exception(resources.getString("databaseError"));

        }
	}

    public ProjectCalendarEntry createProjectCalendarEntry(User user, Project project, String startTime, String endTime,
            String entry) throws Exception {

        int id = -1;
      try {
       
            String query = "INSERT INTO Entry (person_id, project_id, start_time, end_time, entry) "
                    + "VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, project.getId());
            preparedStatement.setString(3, startTime);
            preparedStatement.setString(4, endTime);
            preparedStatement.setString(5, entry);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt("id");
            } else {
            	throw new Exception(resources.getString("databaseError"));
            }
          
           ProjectCalendarEntry calendarEntry = new ProjectCalendarEntry(id, entry, LocalDate.parse(startTime), LocalDate.parse(endTime), project.getGroupLeader());      

            return calendarEntry;

        } catch (SQLException e) {

        	throw new Exception(resources.getString("databaseError"));

        }
        
    }

    public Project createProject(String name, User user) throws Exception {
        int id = -1;
        ResultSet rs;
        try {

            String query = "SELECT id from project " + "WHERE name = ?  AND person_id = ?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, user.getId());
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                throw new Exception(resources.getString("nameInUse"));
            }

            String insertQuery = "INSERT INTO project (name, person_id) " + "VALUES (?, ?)";

            preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
            rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt("id");
            } else {
            	throw new Exception(resources.getString("databaseError"));
            }
            Project project = new Project(id, name, user);
            return project;

        } catch (SQLException e) {
            throw new Exception(resources.getString("databaseError"));
        }

    }

    public LoggedInUser loadLoggedInUser(String userName, String providedPassword) throws Exception {
        LoggedInUser user;
        String query = "SELECT * FROM person " + "WHERE user_name = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userName);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {

            int userId = rs.getInt("id");
            String displayName = rs.getString("display_name");
            String email = rs.getString("user_name");
            String securedPassword = rs.getString("password");
            boolean passwordIsCorrect = PasswordService.verifyUserPassword(providedPassword, securedPassword);
            if(passwordIsCorrect) { 
            	user = new LoggedInUser(userId, email, displayName);
            	loadProjects(user, userId);
            	return user;
            } else {
            	throw new Exception(resources.getString("databaseError"));
            }
        } else {
            throw new Exception(resources.getString("databaseError"));
        }

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

    public void loadProjects(LoggedInUser user, int userId) throws Exception {

        String queryProjectWhereLeader = "SELECT * from project " + "WHERE user_id = ?";
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(queryProjectWhereLeader);
        preparedStatement.setInt(1, userId);

        ResultSet rs = preparedStatement.executeQuery();
        connection.commit();

        while (rs.next()) {
            int id = rs.getInt("project_id");
            String name = rs.getString("name");
            int groupLeaderId = rs.getInt("user_id");
            User groupLeader = loadUser(groupLeaderId);
            Project project = new Project(id,name, groupLeader);
            loadProjectEntries(project);
            user.setProjectWhereLeader(project);
        }

        String queryProjectWhereMember = "SELECT project_member.project_id, project.person_id, project.name FROM project_members "
                + "INNER JOIN project ON project_member.person_id = project.person_id WHERE person_id = ?";
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(queryProjectWhereMember);
        preparedStatement.setInt(1, userId);

        rs = preparedStatement.executeQuery();
        connection.commit();

        while (rs.next()) {
            int projectId = rs.getInt("project_id");
            String name = rs.getString("name");
            int projectLeaderId = rs.getInt("person_id");
            User projectLeader = loadUser(projectLeaderId);
            Project project = new Project(projectId, name, projectLeader);
            loadProjectEntries(project);
            user.setProjectWhereLeader(project);
        }
    }

    public void loadProjectEntries(Project project) throws Exception {
        try {
            String query = "SELECT * from project_entry WHERE project_id = ?";

            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, project.getId());

            ResultSet rs = preparedStatement.executeQuery();
            connection.commit();

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("person_id");
                User user = project.getTeamMember(userId);
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String entry = rs.getString("entry");
                ProjectCalendarEntry projectCalendarEntry = new ProjectCalendarEntry(id, entry, LocalDate.parse(startTime), LocalDate.parse(endTime),user);
               
                project.setEntry(projectCalendarEntry);
            }
        } catch (Exception e) {
            throw new Exception(resources.getString("databaseError"));
        }
    }

    public void loadProjectMembers(Project project) throws Exception {
        try {
            String query = "SELECT project_member.person_id, person.user_name, person.display_name FROM project_members "
                    + "INNER JOIN person ON project_member.person_id = person.id WHERE user_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, project.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("person_id");
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
            String query = "SELECT * from Entry " + "WHERE user_id = ?";

            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getId());

            ResultSet rs = preparedStatement.executeQuery();
            connection.commit();

            while (rs.next()) {
                int id = rs.getInt("id");
                String startDate = rs.getString("start_time");
                String endDate = rs.getString("end_time");
                String entry = rs.getString("entry");
                CalendarEntry calendarEntry = new CalendarEntry(id, entry, LocalDate.parse(startDate), LocalDate.parse(endDate));
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