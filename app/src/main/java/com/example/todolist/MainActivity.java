package com.example.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);

        databaseHelper = new DatabaseHelper(this);
        taskList = databaseHelper.getAllTasks(); // Load tasks from the database
        taskAdapter = new TaskAdapter(taskList, this::editTask, this::deleteTask);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        fab.setOnClickListener(view -> showTaskDialog(false, null));
    }

    private void showTaskDialog(boolean isEditing, Task task) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null);
        EditText etTaskName = dialogView.findViewById(R.id.etTaskName);
        EditText etDescription = dialogView.findViewById(R.id.etDescription);
        EditText etSchedule = dialogView.findViewById(R.id.etSchedule);
        EditText etDuration = dialogView.findViewById(R.id.etDuration);

        if (isEditing && task != null) {
            etTaskName.setText(task.getName());
            etDescription.setText(task.getDescription());
            etSchedule.setText(task.getSchedule());
            etDuration.setText(String.valueOf(task.getDuration()));
        }

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(isEditing ? "Edit Task" : "Add Task")
                .setView(dialogView)
                .setPositiveButton("Save", (dialogInterface, i) -> {
                    String taskName = etTaskName.getText().toString().trim();
                    String description = etDescription.getText().toString().trim();
                    String schedule = etSchedule.getText().toString().trim();
                    String durationStr = etDuration.getText().toString().trim();

                    if (taskName.isEmpty() || schedule.isEmpty() || durationStr.isEmpty()) {
                        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int duration = Integer.parseInt(durationStr);

                    if (isEditing && task != null) {
                        task.setName(taskName);
                        task.setDescription(description);
                        task.setSchedule(schedule);
                        task.setDuration(duration);
                        databaseHelper.updateTask(task); // Update in database
                    } else {
                        Task newTask = new Task(taskList.size() + 1, taskName, description, schedule, duration, false);
                        databaseHelper.insertTask(newTask); // Insert new task into database
                        taskList.add(newTask);
                    }
                    taskAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void editTask(Task task) {
        showTaskDialog(true, task);
    }

    private void deleteTask(Task task) {
        databaseHelper.deleteTask(task); // Delete from database
        taskList.remove(task);
        taskAdapter.notifyDataSetChanged();
    }
}