package com.example.ca1androidapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//DAO - Specify SQL Querys and give them a method to call
@Dao
public interface ExerciseDAO {
    @Query("SELECT * FROM exercise_table")
    LiveData<List<Exercise>> getAllExercises();

    @Query("SELECT * FROM exercise_table WHERE id = :id LIMIT 1")
    Exercise findExerciseById(long id);

    @Query("SELECT * FROM exercise_table WHERE name LIKE :name LIMIT 1")
    LiveData<List<Exercise>> findExerciseByName(String name);

    @Query("SELECT COUNT(*) FROM exercise_table")
    int rowCount();

    @Insert
    void insertExercises(Exercise... exercises);

    @Update
    void updateExercises(Exercise... exercises);

    @Delete
    void deleteExercises(Exercise... exercises);
}
