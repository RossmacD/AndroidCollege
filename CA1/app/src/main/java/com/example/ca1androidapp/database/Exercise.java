package com.example.ca1androidapp.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_table")
public class Exercise {

    //Vars
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "sets")
    private int sets;

    @ColumnInfo(name = "reps")
    private int reps;

    @ColumnInfo(name = "weight")
    private float weight;

    //Constructors
    public Exercise(){

    }

    //Old Constructor
    @Ignore
    public Exercise(String name){
        this.name=name;
    }

    @Ignore
    public Exercise(String name, int sets, int reps, float weight){
        this.name=name;
        this.sets=sets;
        this.reps=reps;
        this.weight=weight;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int set) {
        this.sets = set;
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
