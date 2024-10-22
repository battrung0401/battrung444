package com.example.androidtopic2;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    public TaskViewModel(Application application) {
        super(application);

        TaskDatabase database = TaskDatabase.getDatabase(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insertTask(Task task) {
        new Thread(() -> taskDao.insert(task)).start();
    }
}
