package com.example.ca1androidapp.database;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {
    public static void populateAsync(final ExerciseDatabase database) {
        new PopulateDbAsync(database).execute();
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ExerciseDatabase database;

        PopulateDbAsync(ExerciseDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // If the Database is empty, add the initial data.
            if (database.exerciseDAO().rowCount() == 0) {
                List<Exercise> exercises = new ArrayList<>();
                exercises.add(new Exercise("Exercise #1"));
                exercises.add(new Exercise("Exercises #2"));
                exercises.add(new Exercise("Exercises #3"));

                database.exerciseDAO()
                        .insertExercises(
                                exercises.toArray(new Exercise[exercises.size()]));
            }

            return null;
        }
    }
}
