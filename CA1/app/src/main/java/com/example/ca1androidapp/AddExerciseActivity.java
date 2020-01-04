package com.example.ca1androidapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;

import com.example.ca1androidapp.database.Exercise;
import com.example.ca1androidapp.database.ExerciseDAO;
import com.example.ca1androidapp.database.ExerciseDatabase;
import com.example.ca1androidapp.databinding.ActivityAddExerciseBinding;

import java.util.List;

public class AddExerciseActivity extends AppCompatActivity {
    private  Exercise exercise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddExerciseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_exercise);

        //Pass Id through to the fragment of editing
        if(getIntent().getExtras() != null) {
            int exerciseId = getIntent().getExtras().getInt("exerciseId");
            Log.d("Rossi","Id is:" + exerciseId);
            new SelectExerciseTask(exerciseId, this, binding).execute();;
           //exercise =  ExerciseDatabase.getInstance(getApplicationContext()).exerciseDAO().findExerciseById(exerciseId);

        }


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
            Log.d("Rossi","Exercise is:" + exercise);
            binding.setExercise(exercise);
         return null;
        }
    }
}

