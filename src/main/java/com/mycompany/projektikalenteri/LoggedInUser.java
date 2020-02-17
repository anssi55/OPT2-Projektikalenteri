package com.mycompany.projektikalenteri;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mycompany.projektikalenteri.User;

public class LoggedInUser extends User {
    private DatabaseService dBService;

    private List<Project> projectsWhereLeader;

    private List<Project> projectsWhereMember;

    private ArrayList<CalendarEntry> calendarEntries;

    public LoggedInUser(int id, String userName, String displayName) {
        super(id, userName, displayName);
        this.projectsWhereLeader = new ArrayList<Project>();
        this.projectsWhereMember = new ArrayList<Project>();
        this.calendarEntries = new ArrayList<CalendarEntry>();
    }

    public void setProjectWhereLeader(Project project) {
        this.projectsWhereLeader.add(project);
    }

    public void setProjectWhereMember(Project project) {
        this.projectsWhereMember.add(project);
    }

    public void setEntry(CalendarEntry entry) {
        this.calendarEntries.add(entry);
    }

    public List<Project> getProjectsWhereLeader() {
        return this.projectsWhereLeader;
    }

    public List<Project> getProjectsWhereMember() {
        return this.projectsWhereMember;
    }

    public List<CalendarEntry> getEntries() {
        return this.calendarEntries;
    }

    public void setDatabaseService(DatabaseService dBService) {
        this.dBService = dBService;
    }

    public void createProject(String name) throws Exception {
        Project project = dBService.createProject(name, this);
        setProjectWhereLeader(project);
    }

    public void CreatePersonalEntry(String startTime, String endTime, String entryMessage) throws Exception {
        CalendarEntry calendarEntry = dBService.createCalendarEntry(this, startTime, endTime, entryMessage);
        setEntry(calendarEntry);
    }

    public void createProjectEntry(String startTime, String endTime, String entryMessage, Project project)
            throws Exception {
        try {
            dBService.createProjectCalendarEntry(this, project, startTime, endTime, entryMessage);

        } catch (Exception e) {
            throw e;
        }
    }

    public void removeProject(Project project) throws Exception {
        try {
            dBService.deleteProject(project.getId());
            deleteProjectFromList(project);
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteProjectFromList(Project project) {
        Iterator<Project> iterator = this.projectsWhereLeader.iterator();
        while (iterator.hasNext()) {
            Project p = iterator.next();
            if (p.equals(project)) {
                iterator.remove();
            }
        }
    }

}
