package com.example.ttscoach;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.ttscoach.database.Exercise;
import com.example.ttscoach.database.ExerciseDatabase;
import com.example.ttscoach.databinding.ActivityAddExerciseBinding;

public class AddExerciseActivity extends AppCompatActivity {
    private  Exercise exercise;
    private Boolean isUpdating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddExerciseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_exercise);

        //Pass Id through to the fragment of editing
        if(getIntent().getExtras() != null) {
            isUpdating=true;
            int exerciseId = getIntent().getExtras().getInt("exerciseId");
            new SelectExerciseTask(exerciseId, this, binding).execute();;
        }else{
            isUpdating=false;
        }


        //Update or create
        binding.addExerciseButton.setOnClickListener((view) -> {
            String exerciseNameFieldContent = binding.addExerciseNameField.getText().toString().trim();
            int exerciseRepsFieldContent = Integer.parseInt(binding.addExerciseRepsField.getText().toString().trim());
            int exerciseIntervalFieldContent =  Math.round(binding.intervalSlider.getValue());
            int exerciseSetsFieldContent = Integer.parseInt(binding.addExerciseSetsField.getText().toString().trim());
            int exerciseSetBreakFieldContent =  Math.round(binding.setBreakSlider.getValue());

            //Choose whether to update or create
            if (!exerciseNameFieldContent.isEmpty()) {
                if(isUpdating){
                    Exercise exercise=binding.getExercise();
                    exercise.setName(exerciseNameFieldContent);
                    exercise.setReps(exerciseRepsFieldContent);
                    exercise.setInterval(exerciseIntervalFieldContent);
                    exercise.setSets(exerciseSetsFieldContent);
                    exercise.setSetBreak(exerciseSetBreakFieldContent);
                    new UpdateExerciseTask(binding.getExercise()).execute();
                    finish();
                }else {
                    new AddExerciseTask(exerciseNameFieldContent, exerciseRepsFieldContent,exerciseIntervalFieldContent,exerciseSetsFieldContent,exerciseSetBreakFieldContent).execute();
                    finish();
                }
            }
        });

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private static class SelectExerciseTask extends AsyncTask<Void, Void, Void> {
        Exercise exercise;
        Context context;
        int id;
        ActivityAddExerciseBinding binding;

        public SelectExerciseTask(int id,Context context,ActivityAddExerciseBinding binding) {
            this.context=context;
            this.id = id;
            this.binding=binding;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            exercise = ExerciseDatabase.getInstance(context).exerciseDAO().findExerciseById(id);
            binding.setExercise(exercise);
         return null;
        }
    }

    private class AddExerciseTask extends AsyncTask<Void, Void, Void> {
        String exerciseName;
        int reps;
        int interval;
        int sets;
        int setBreak;

        public AddExerciseTask(String exerciseName,int reps,int interval, int sets, int setBreak) {
            this.exerciseName = exerciseName;
            this.reps=reps;
            this.interval=interval;
            this.sets=sets;
            this.setBreak=setBreak;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ExerciseDatabase.getInstance(getApplication()).exerciseDAO().insertExercises(new Exercise( exerciseName, reps, interval,  sets,  setBreak));
            return null;
        }
    }

    private class UpdateExerciseTask extends AsyncTask<Void, Void, Void> {
        Exercise exercise;

        public UpdateExerciseTask(Exercise exercise) {
            this.exercise=exercise;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ExerciseDatabase.getInstance(getApplication()).exerciseDAO().updateExercises(exercise);
            return null;
        }
        @Override
        protected void onPostExecute(Void v){

        }
    }
}

