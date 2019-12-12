package com.example.ca1androidapp.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private final LiveData<List<Exercise>> exercise;

    public ExerciseViewModel(@NonNull Application application) {
        super(application);

        exercise = ExerciseDatabase
                .getInstance(getApplication())
                .exerciseDAO()
                .getAllExercises();
    }

    public LiveData<List<Exercise>> getExercise() {
        return exercise;
    }
}

