package com.example.androidtopic2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.topic2b.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTaskTitle, editTextTaskDescription, editTextTaskTime;
    private Button buttonAddTask, buttonGoToCustomerDetail;
    private ListView listViewTasks;
    private TaskDatabase taskDatabase;
    private TaskDao taskDao;
    private TaskAdapter taskAdapter;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);


        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "task-database")
                .allowMainThreadQueries()
                .build();
        taskDao = taskDatabase.taskDao();


        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        editTextTaskTime = findViewById(R.id.editTextTaskTime);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        buttonGoToCustomerDetail = findViewById(R.id.buttonGoToCustomerDetail);
        listViewTasks = findViewById(R.id.listViewTasks);


        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);


        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter = new TaskAdapter(MainActivity.this, tasks);
                listViewTasks.setAdapter(taskAdapter);
            }
        });

        // Thêm tác vụ mới
        buttonAddTask.setOnClickListener(v -> {
            String title = editTextTaskTitle.getText().toString();
            String description = editTextTaskDescription.getText().toString();
            String time = editTextTaskTime.getText().toString();

            if (title.isEmpty() || description.isEmpty() || time.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }


            Task task = new Task(title, description, time);
            taskViewModel.insertTask(task);
            editTextTaskTitle.setText("");
            editTextTaskDescription.setText("");
            editTextTaskTime.setText("");
        });


        buttonGoToCustomerDetail.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CustomerDetailActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }
}
