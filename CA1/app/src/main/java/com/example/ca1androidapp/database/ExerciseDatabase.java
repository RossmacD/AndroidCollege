package com.example.ca1androidapp.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Creates the Database
@Database(entities = {Exercise.class}, version = 1, exportSchema = false)
    public abstract class ExerciseDatabase extends RoomDatabase {

        private static ExerciseDatabase INSTANCE;

        public static ExerciseDatabase getInstance(Context context) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ExerciseDatabase.class, "exercise_table").build();
            }
            return INSTANCE;

        }

        public static void destroyInstance() {
            INSTANCE = null;
        }
        public abstract ExerciseDAO exerciseDAO();
    }

