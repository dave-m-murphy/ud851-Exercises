package com.example.android.todolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskEntry;

public class AddTaskViewModelRepo extends ViewModel {
    /**
     * AndroidViewModel is subclass of ViewModel that allows passing of context (application)
     * in the constructor. In this case, need context to get database instance from repo
     */

    private LiveData<TaskEntry> task;

    // Create a constructor where you call loadTaskById of the taskDao to initialize the task variable
    // Note: The constructor should receive the database and the taskId
    public AddTaskViewModelRepo(AppDatabase database, int taskId) {
        task = database.taskDao().loadTaskById(taskId);
    }

    public LiveData<TaskEntry> getTask() {
        return task;
    }
}
