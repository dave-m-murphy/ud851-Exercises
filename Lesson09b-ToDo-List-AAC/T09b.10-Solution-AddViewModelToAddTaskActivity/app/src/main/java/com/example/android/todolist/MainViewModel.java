package com.example.android.todolist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    /**
     * AndroidViewModel is subclass of ViewModel that allows passing of context (application)
     * in the constructor. In this case, need context to get database instance from repo
     */

    private static final String TAG = MainViewModel.class.getSimpleName();
    private TasksRepository tasksRepository;
    private LiveData<List<TaskEntry>> allTaskEntries;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "Actively retrieving the tasks not from the DataBase, but from REPOSITORY");
//        AppDatabase database = AppDatabase.getInstance(this.getApplication());
////        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
////        tasks = database.taskDao().loadAllTasks();
        tasksRepository = new TasksRepository(application);
        allTaskEntries = tasksRepository.getAllTasks();
    }

//    public LiveData<List<TaskEntry>> getTasks() {
//        return allTasks;
//    }

    public void insert(TaskEntry taskEntry) {
        tasksRepository.insert(taskEntry);
    }

    public void update(TaskEntry taskEntry) {
        tasksRepository.update(taskEntry);
    }

    public void delete(TaskEntry taskEntry) {
        tasksRepository.delete(taskEntry);
    }

    public LiveData<List<TaskEntry>> getTasks() {
        return allTaskEntries;
    }
}
