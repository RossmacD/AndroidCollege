package com.example.ca1androidapp.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Exercise {

    //Vars
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "set")
    private int set;

    @ColumnInfo(name = "reps")
    private int reps;

    @ColumnInfo(name = "weight")
    private float weight;

    //Constructors
    public Exercise(){

    }

    @Ignore
    public Exercise(String exerciseName){
        this.name=exerciseName;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExerciseName() {
        return name;
    }

    public void setExerciseName(String exerciseName) {
        this.name = name;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
