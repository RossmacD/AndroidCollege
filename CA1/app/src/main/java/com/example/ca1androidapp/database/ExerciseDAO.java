package com.example.ca1androidapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExerciseDAO {
    @Query("SELECT * FROM exercise")
    LiveData<List<Exercise>> getAllExercises();

    @Query("SELECT * FROM exercise WHERE id = :id LIMIT 1")
    LiveData<List<Exercise>> findExerciseById(long id);

    @Query("SELECT * FROM exercise WHERE name LIKE :name LIMIT 1")
    LiveData<List<Exercise>> findExerciseByName(String name);

    @Query("SELECT COUNT(*) FROM exercise")
    int rowCount();

    @Insert
    void insertExercises(Exercise... exercises);

    @Update
    void updateBlogPosts(Exercise... exercises);

    @Delete
    void deleteBlogPosts(Exercise... exercises);
}
