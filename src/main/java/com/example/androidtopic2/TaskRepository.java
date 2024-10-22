package com.example.androidtopic2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskRepository(Application application) {
        // Khởi tạo db trước khi sử dụng
        TaskDatabase db = TaskDatabase.getDatabase(application);  // Khởi tạo TaskDatabase
        taskDao = db.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public void insert(Task task) {

        TaskDatabase.getDatabaseWriteExecutor().execute(() -> taskDao.insert(task));
    }

    public void delete(Task task) {
        TaskDatabase.getDatabaseWriteExecutor().execute(() -> taskDao.delete(task));
    }


    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }
}
