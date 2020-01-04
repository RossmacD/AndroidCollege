package com.example.ca1androidapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.ca1androidapp.database.Exercise;
import com.example.ca1androidapp.database.ExerciseDatabase;
import com.example.ca1androidapp.databinding.ActivityAddExerciseBinding;
import com.example.ca1androidapp.databinding.NewActivityAddExerciseBinding;

public class NewAddExerciseActivity extends AppCompatActivity {
    private  Exercise exercise;
    private Boolean isUpdating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewActivityAddExerciseBinding binding = DataBindingUtil.setContentView(this, R.layout.new_activity_add_exercise);

        //Pass Id through to the fragment of editing
        if(getIntent().getExtras() != null) {
            isUpdating=true;
            int exerciseId = getIntent().getExtras().getInt("exerciseId");
            Log.d("Rossi","Id is:" + exerciseId);
            new SelectExerciseTask(exerciseId, this, binding).execute();;
           //exercise =  ExerciseDatabase.getInstance(getApplicationContext()).exerciseDAO().findExerciseById(exerciseId);

        }else{
            isUpdating=false;
        }


        binding.addExerciseButton.setOnClickListener((view) -> {
            String exerciseNameFieldContent = binding.addExerciseNameField.getText().toString().trim();
            int exerciseSetsFieldContent = Integer.parseInt(binding.addExerciseSetsField.getText().toString().trim());
            int exerciseRepsFieldContent = Integer.parseInt(binding.addExerciseRepsField.getText().toString().trim());
            float exerciseWeightFieldContent = Float.parseFloat(binding.addExerciseWeightField.getText().toString());

            if (!exerciseNameFieldContent.isEmpty()) {
                if(isUpdating){
                    Log.d("Rossi","Exercise is:" + binding.getExercise().getId() + " : " + binding.getExercise().getName());
                    Exercise exercise=binding.getExercise();
                    exercise.setName(exerciseNameFieldContent);
                    exercise.setSets(exerciseSetsFieldContent);
                    exercise.setReps(exerciseRepsFieldContent);
                    exercise.setWeight(exerciseWeightFieldContent);
                    new UpdateExerciseTask(binding.getExercise()).execute();
                    finish();
                }else {
                    new AddExerciseTask(exerciseNameFieldContent,exerciseSetsFieldContent,exerciseRepsFieldContent,exerciseWeightFieldContent).execute();
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
        NewActivityAddExerciseBinding binding;

        public SelectExerciseTask(int id,Context context,NewActivityAddExerciseBinding binding) {
            this.context=context;
            this.id = id;
            this.binding=binding;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            exercise = ExerciseDatabase.getInstance(context).exerciseDAO().findExerciseById(id);
            Log.d("Rossi","Exercise is:" + exercise);
            binding.setExercise(exercise);
         return null;
        }
    }

    private class AddExerciseTask extends AsyncTask<Void, Void, Void> {
        String exerciseName;
        int sets;
        int reps;
        float weight;

        public AddExerciseTask(String exerciseName,int sets,int reps, float weight) {
            this.exerciseName = exerciseName;
            this.sets=sets;
            this.reps=reps;
            this.weight=weight;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ExerciseDatabase.getInstance(getApplication()).exerciseDAO().insertExercises(new Exercise(exerciseName,sets,reps,weight));
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
    }
}

