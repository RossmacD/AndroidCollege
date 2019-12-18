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
            String exerciseTitleEditTextContent = binding.addExerciseTextInputEditText.getText().toString().trim();

            if (!exerciseTitleEditTextContent.isEmpty()) {
                new AddExerciseTask(exerciseTitleEditTextContent).execute();
                getActivity().finish();
            }
        });

        return binding.getRoot();
    }

    private class AddExerciseTask extends AsyncTask<Void, Void, Void> {
        String exerciseName;

        public AddExerciseTask(String exerciseName) {
            this.exerciseName = exerciseName;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ExerciseDatabase.getInstance(getActivity().getApplication()).exerciseDAO().insertExercises(new Exercise(exerciseName));
            return null;
        }
    }
}
