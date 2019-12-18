package com.example.ca1androidapp.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Exercise.class}, version = 1, exportSchema = false)
    public abstract class ExerciseDatabase extends RoomDatabase {

        private static ExerciseDatabase INSTANCE;

        public static ExerciseDatabase getInstance(Context context) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        ExerciseDatabase.class,
                        "exercise_table")
                        .build();
            }
            return INSTANCE;

//            if(instance == null) {
//                synchronized (LOCK) {
//                    if (instance == null) {
//                        instance = Room.databaseBuilder(context.getApplicationContext(),
//                                AppDatabase.class, DATABASE_NAME).build();
//                    }
//                }
//            }
        }

        public static void destroyInstance() {
            INSTANCE = null;
        }

        public abstract ExerciseDAO exerciseDAO();
    }

