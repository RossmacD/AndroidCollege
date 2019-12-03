package com.example.ca1androidapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseDAO {

    @Query("SELECT * FROM exercise")
    LiveData<List<Exercise>> getAllExercises();

    @Query("SELECT * FROM exercise WHERE id= :id LIMIT 1")
    LiveData<List<Exercise>> findExerciseById();

    @Query("SELECT * FROM exercise WHERE name LIKE :name LIMIT 1")
    LiveData<List<Exercise>> findExerciseByName();

}
