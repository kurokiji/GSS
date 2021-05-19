package com.kurokiji.gss;

public class LogEntry {
    private String event;
    private long date;

    public LogEntry(String event, long date) {
        this.event = event;
        this.date = date;
    }

    public String getEvent(){
        return this.event;
    }

    public long getDate(){
        return this.date;
    }
}
