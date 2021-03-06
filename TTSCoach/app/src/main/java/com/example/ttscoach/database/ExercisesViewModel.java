package com.example.ttscoach.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExercisesViewModel extends AndroidViewModel {

    private final LiveData<List<Exercise>> exercises;

    public ExercisesViewModel(@NonNull Application application) {
        super(application);
        exercises = ExerciseDatabase.getInstance(getApplication()).exerciseDAO().getAllExercises();
    }

    public LiveData<List<Exercise>> getExercise() {
        return exercises;
    }

    public Exercise getExerciseById(long id){
        Exercise exercise =  ExerciseDatabase.getInstance(getApplication()).exerciseDAO().findExerciseById(id);
        return exercise;
    }
}

