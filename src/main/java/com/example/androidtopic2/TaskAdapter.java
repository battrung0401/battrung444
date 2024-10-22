
package com.example.androidtopic2;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.topic2b.R;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private Context context;
    private List<Task> tasks;

    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, R.layout.task_item, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        }

        Task task = tasks.get(position);

        TextView titleTextView = convertView.findViewById(R.id.textViewTaskTitle);
        TextView timeTextView = convertView.findViewById(R.id.textViewTaskTime);

        titleTextView.setText(task.getTitle());
        timeTextView.setText(task.getTime());

        return convertView;
    }


    public void addTask(Task task) {
        tasks.add(task);
        notifyDataSetChanged();
    }
}
