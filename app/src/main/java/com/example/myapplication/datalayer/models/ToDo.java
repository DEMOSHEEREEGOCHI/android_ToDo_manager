package com.example.myapplication.datalayer.models;

public class ToDo {
   private String id;
   private static String title;
   private boolean isComplete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}