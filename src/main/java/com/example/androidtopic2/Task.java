package com.example.androidtopic2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")  // Xác định tên bảng là task_table
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String time;

    public Task(String title, String description, String time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }

    // Các getter và setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
