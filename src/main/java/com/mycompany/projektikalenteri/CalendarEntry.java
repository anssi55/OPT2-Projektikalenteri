package com.mycompany.projektikalenteri;

import java.time.LocalDate;

public class CalendarEntry {
    private int id;
    private String entry;
    private LocalDate startTime;
    private LocalDate endTime;

    public CalendarEntry(int id, String entry, LocalDate startTime, LocalDate endTime) {
        this.id = id;
        this.entry = entry;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setTimes(LocalDate startTime, LocalDate endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntry() {
        return this.entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public LocalDate getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

}
