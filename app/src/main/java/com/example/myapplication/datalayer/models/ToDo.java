package com.example.myapplication.datalayer.models;

import android.content.Context;

import java.util.Objects;

public class ToDo {
   private String id;
   private String title;
   private boolean isComplete;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo toDo = (ToDo) o;
        return isComplete == toDo.isComplete &&
                Objects.equals(id, toDo.id) &&
                Objects.equals(title, toDo.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, isComplete);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
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
