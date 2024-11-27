package com.example.todolist;

public class Task {
    private int id;
    private String name;
    private String description;
    private String schedule;  // Format: "yyyy-MM-dd HH:mm"
    private int duration;     // Time in minutes
    private boolean isCompleted; // New field for completion status

    public Task(int id, String name, String description, String schedule, int duration, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.schedule = schedule;
        this.duration = duration;
        this.isCompleted = isCompleted; // Initialize new field
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}