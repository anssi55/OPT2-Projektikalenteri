package com.mycompany.projektikalenteri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import main.java.com.mycompany.projektikalenteri.ProjectCalendarEntry;
import main.java.com.mycompany.projektikalenteri.User;

public class Project {
    private int id;
    private String name;
    private User groupLeader;
    private ArrayList<User> teamMembers;
    private ArrayList<ProjectCalendarEntry> calendarEntries;

    public Project(int id, String name, User groupLeader) {
        this.id = id;
        this.name = name;
        this.groupLeader = groupLeader;
        this.teamMembers = new ArrayList<User>();
        this.calendarEntries = new ArrayList<ProjectCalendarEntry>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGroupLeader(User groupLeader) {
        this.groupLeader = groupLeader;
    }

    public String getGroupLeader() {
        return this.groupLeader;
    }

    public User getTeamMember(int id) {
        User user = null;
        for (User u : this.teamMembers) {
            if (u.getId() == id) {
                user = u;
            }
        }
        return user;
    }

    public ArrayList<User> getTeamMembers() {
        return this.teamMembers;
    }

    public void setTeamMember(User teamMember) {
        this.teamMembers.add(teamMember);
    }

    public void setEntry(ProjectCalendarEntry calendarEntry) {
        this.calendarEntries.add(calendarEntry);
    }

    @Override
    public String toString() {
        return this.name + " " + this.id;
    }

}
