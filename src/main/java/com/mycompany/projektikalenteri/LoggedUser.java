package com.mycompany.projektikalenteri;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoggedUser extends User {
    private DatabaseService dBService;

    private List<Projekti> projectsWhereLeader;

    private List<Projekti> projectsWhereMember;

    private ArrayList<CalendarEntry> calendarEntries;

    public LoggedUser(int id, String userName, String displayName) {
        super(id, userName, displayName);
        this.projectsWhereLeader = new ArrayList<Projekti>();
        this.projectsWhereMember = new ArrayList<Projekti>();
        this.calendarEntries = new ArrayList<Kalenterimerkinta>();
    }

    public void setProjectWhereLeader(Projekti project) {
        this.projectsWhereLeader.add(project);
    }

    public void setProjectWhereMember(Projekti project) {
        this.projectsWhereMember.add(project);
    }

    public void setEntry(Entry entry) {
        this.calendarEntries.add(entry);
    }

    public List<Projekti> getProjectsWhereLeader() {
        return this.projectsWhereLeader;
    }

    public List<Projekti> getProjectsWhereMember() {
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
            Projekti p = iterator.next();
            if (p.equals(project)) {
                iterator.remove();
            }
        }
    }

}
