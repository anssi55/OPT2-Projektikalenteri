package com.mycompany.projektikalenteri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class Project {
    private int id;
    private String name;
    private User groupLeader;
    private ArrayList<User> teamMembers;
    private ArrayList<Kalenterimerkinta> calendarEntries;
    private ResourceBundle resources;

    public Projekti(int id, String name, String groupLeader, ResourceBundle resources ) {
    	this.id = id;
    	this.name = name;
        this.groupLeader = groupLeader;
        this.teamMembers = new ArrayList<User>();
        this.calendarEntries = new ArrayList<Kalenterimerkinta>();
        this.resources = resources;
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

    public void setGroupLeader(User groupLeader) {
        this.groupLeader = groupLeader;
    }

    public ArrayList<String> getTeamMemberst() {
        return this.teamMembers;
    }

    public void setTeamMember(User teamMember) {
        this.teamMembers.add(teamMember);
    }

    public void setEntry(CalendarEntry calendarEntry) {
        this.calendarEntries.add(calendarEntry);
    }

    @Override
    public String toString() {
        return resources.getString("name") + " " + this.name + " Id: " + this.id;
    }

}
