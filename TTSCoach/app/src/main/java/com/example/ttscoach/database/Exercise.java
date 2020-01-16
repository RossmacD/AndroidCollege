package com.example.ttscoach.database;
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

    @ColumnInfo(name = "reps")
    private int reps;

    @ColumnInfo(name = "interval")
    private int interval;

    @ColumnInfo(name = "sets")
    private int sets;

    @ColumnInfo(name = "set_break")
    private int setBreak;


    //Constructors
    public Exercise(){

    }

    @Ignore
    public Exercise(String name, int reps, int sets, int interval,int setBreak){
        this.name=name;
        this.sets=sets;
        this.reps=reps;
        this.interval=interval;
        this.setBreak=setBreak;
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

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int set) {
        this.sets = set;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getSetBreak() {
        return setBreak;
    }

    public void setSetBreak(int setBreak) {
        this.setBreak = setBreak;
    }
}
