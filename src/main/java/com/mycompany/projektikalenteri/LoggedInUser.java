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

    public void setEntry(Entry entry) {
        this.calendarEntries.add(entry);
    }

    public List<Project> getProjectsWhereLeader() {
        return this.projectsWhereLeader;
    }

    public List<Project> getProjectsWhereMember() {
        return this.projectsWhereMember;
    }

    public List<Entry> getEntries() {
        return this.entries;
    }

    public void setDatabaseService(DatabaseService dBService) {
        this.dBService = dBService;
    }

    public void createProject(String name) throws Exception {
        Project project = databaseService.createProject(name, this);
        setProjectWhereLeader(project);
    }

    public void CreatePersonalEntry(LocalDate startTime, LocalDate endTime, String entryMessage) throws Exception {
        Entry entry = dBService.createUserEntry(this, start, end, entryMessage);
        setEntry(entry);
    }

    public void createProjectEntry(LocalDate startTime, LocalDate endTime, String entryMessage, Project project)
            throws Exception {
        ProjectEntry projectEntry = dBService.addProjectEntry(this, project, startTime, endTime, entryMessage);
        project.setEntry(projectEntry);
    }

    public void removeProject(Project project) throws Exception {
        dBService.removeProject(project, this);
        removeProjectFromList(project);
    }

    public void removeProjectFromList(Project project) {
        Iterator<Project> iterator = this.projectsWhereLeader.iterator();
        while (iterator.hasNext()) {
            Project p = iterator.next();
            if (p.equals(project)) {
                iterator.remove();
            }
        }
    }

}
