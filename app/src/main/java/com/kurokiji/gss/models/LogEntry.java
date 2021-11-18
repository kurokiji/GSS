package com.kurokiji.gss.models;

public class LogEntry {
    private String event;
    private long date;
    private boolean newDate;

    public LogEntry(String event, long date, boolean newDate) {
        this.event = event;
        this.date = date;
        this.newDate = newDate;
    }

    public String getEvent(){
        return this.event;
    }

    public long getDate(){
        return this.date;
    }

    public boolean getIsNewDate() {
        return newDate;
    }

    public void setNewDate(boolean newDate) {
        this.newDate = newDate;
    }
}
