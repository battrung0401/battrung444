package com.example.androidtopic2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);


    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllTasks(); // Trả về LiveData<List<Task>>


    @Delete
    void delete(Task task);
}
