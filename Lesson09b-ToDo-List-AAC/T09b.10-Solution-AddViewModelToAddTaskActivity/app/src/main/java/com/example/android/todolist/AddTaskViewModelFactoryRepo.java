package com.example.android.todolist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.todolist.database.AppDatabase;

public class AddTaskViewModelFactoryRepo extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mTaskId;

    public AddTaskViewModelFactoryRepo(AppDatabase database, int taskId) {
        mDb = database;
        mTaskId = taskId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddTaskViewModel(mDb, mTaskId);
    }
}
