package com.example.android.todolist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskDao;
import com.example.android.todolist.database.TaskEntry;

import java.util.List;

public class TasksRepository {
    private TaskDao taskDao;
    private LiveData<TaskEntry> task;
    private LiveData<List<TaskEntry>> allTasks;

    // Not sure what implications of offering >1 constructor for this repo?

    public TasksRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.loadAllTasks();
    }

    // separate constructor for AddTaskActivity
    public TasksRepository(AppDatabase database, int id) {
        taskDao = database.taskDao();
        task = taskDao.loadTaskById(id);
        allTasks = taskDao.loadAllTasks();
    }

    // these are the API that the repository exposes to external clients.
    // eg. the ViewModel
    /**
     * GET ALL
     */
    public LiveData<List<TaskEntry>> getAllTasks() {
        return allTasks;
    }

    /**
     * GET ONE BY ID
     */
    public LiveData<TaskEntry> loadTaskById(int id) {
        return task;
    }

    /**
     * INSERT
     */
    public void insert(TaskEntry taskEntry) {
        new InsertTaskAsyncTask(taskDao).execute(taskEntry);
    }

    private static class InsertTaskAsyncTask extends AsyncTask<TaskEntry, Void, Void> {
        private TaskDao taskDao;

        // since the class is static we cannot access the TaskDao instance of our repo directly
        // so we have to pass a constructor for the class
        private InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntry... taskEntries) {
            taskDao.insertTask(taskEntries[0]); // Varargs is similar to any array. Accepts arbitrary number of values
            return null;
        }
    }

    /**
     * UPDATE
     */
    public void update(TaskEntry taskEntry) {
        new UpdateTaskAsyncTask(taskDao).execute(taskEntry);
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<TaskEntry, Void, Void> {
        private TaskDao taskDao;

        // since the class is static we cannot access the TaskDao instance of our repo directly
        // so we have to pass a constructor for the class
        private UpdateTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntry... taskEntries) {
            taskDao.updateTask(taskEntries[0]); // Varargs is similar to any array. Accepts arbitrary number of values
            return null;
        }
    }

    /**
     * DELETE
     */
    public void delete(TaskEntry taskEntry) {
        new DeleteTaskAsyncTask(taskDao).execute(taskEntry);
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<TaskEntry, Void, Void> {
        private TaskDao taskDao;

        // since the class is static we cannot access the TaskDao instance of our repo directly
        // so we have to pass a constructor for the class
        private DeleteTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntry... taskEntries) {
            taskDao.deleteTask(taskEntries[0]); // Varargs is similar to any array. Accepts arbitrary number of values
            return null;
        }
    }
}
