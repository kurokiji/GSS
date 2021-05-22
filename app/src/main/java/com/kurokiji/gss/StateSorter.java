package com.kurokiji.gss;

import java.util.Comparator;

public class StateSorter implements Comparator<LogEntry> {

    @Override
    public int compare(LogEntry l1, LogEntry l2){
        if(l1.getDate() < l2.getDate()) return -1;
        else if(l1.getDate() == (l2.getDate())) return 0;
        else return 1;
    }

}
