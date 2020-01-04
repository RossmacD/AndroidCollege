package com.example.ca1androidapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.ca1androidapp.database.Exercise;
import com.example.ca1androidapp.database.ExerciseDatabase;
import com.example.ca1androidapp.databinding.FragmentAddExerciseBinding;

public class AddExerciseActivityFragment extends Fragment {
    public AddExerciseActivityFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentAddExerciseBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_exercise, container, false);

        binding.addExerciseButton.setOnClickListener((view) -> {
            String exerciseNameFieldContent = binding.addExerciseNameField.getText().toString().trim();
            int exerciseSetsFieldContent = Integer.parseInt(binding.addExerciseSetsField.getText().toString().trim());
            int exerciseRepsFieldContent = Integer.parseInt(binding.addExerciseRepsField.getText().toString().trim());
            float exerciseWeightFieldContent = Float.parseFloat(binding.addExerciseWeightField.getText().toString());

            if (!exerciseNameFieldContent.isEmpty()) {
                new AddExerciseTask(exerciseNameFieldContent,exerciseSetsFieldContent,exerciseRepsFieldContent,exerciseWeightFieldContent).execute();
                getActivity().finish();
            }
        });

        return binding.getRoot();
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
            ExerciseDatabase.getInstance(getActivity().getApplication()).exerciseDAO().insertExercises(new Exercise(exerciseName,sets,reps,weight));
            return null;
        }
    }
}
