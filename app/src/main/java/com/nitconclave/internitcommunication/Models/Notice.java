package com.nitconclave.internitcommunication.Models;

public class Notice {
    private int priority;
    private String title;
    private String url;

    public Notice(){

    }

    public Notice(int priority, String title, String url){
        this.priority = priority;
        this.title = title;
        this.url = url;
    }

    public int getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
