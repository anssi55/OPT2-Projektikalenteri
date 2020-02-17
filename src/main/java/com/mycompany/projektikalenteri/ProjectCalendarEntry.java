package com.mycompany.projektikalenteri;

import java.time.LocalDate;

import com.mycompany.projektikalenteri.CalendarEntry;

public class ProjectCalendarEntry extends CalendarEntry {
    private User user;

    public ProjectCalendarEntry(int id, String entry, LocalDate startTime, LocalDate endTime, User user) {
        super(id, entry, startTime, endTime);
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
