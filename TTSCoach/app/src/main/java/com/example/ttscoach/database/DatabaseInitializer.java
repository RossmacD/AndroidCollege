package com.example.ttscoach.database;

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
                exercises.add(new Exercise("Push ups",8,2,1,5));
                exercises.add(new Exercise("Jumping Jacks",4,5,1,3));
                exercises.add(new Exercise("Squat",6,1,1,12));


                database.exerciseDAO().insertExercises(exercises.toArray(new Exercise[exercises.size()]));
            }

            return null;
        }
    }
}
