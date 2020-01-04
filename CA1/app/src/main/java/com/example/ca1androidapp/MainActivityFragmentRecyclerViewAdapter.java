package com.example.ca1androidapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ca1androidapp.database.DatabaseInitializer;
import com.example.ca1androidapp.database.Exercise;
import com.example.ca1androidapp.database.ExerciseDatabase;
import com.example.ca1androidapp.databinding.RecyclerItemBinding;

import java.util.List;

public class MainActivityFragmentRecyclerViewAdapter extends RecyclerView.Adapter<MainActivityFragmentRecyclerViewAdapter.MainActivityFragmentRecyclerViewHolder> {

    private List<Exercise> exercises;
    private Context context;


    public MainActivityFragmentRecyclerViewAdapter(List<Exercise> exercises, Context context) {
        this.context=context;
        this.exercises = exercises;
    }

    @Override
    public MainActivityFragmentRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerItemBinding itemBinding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new MainActivityFragmentRecyclerViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(MainActivityFragmentRecyclerViewHolder holder, int position) {
        Exercise exercise=exercises.get(position);
        holder.bind(exercise,context);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    static class MainActivityFragmentRecyclerViewHolder
            extends RecyclerView.ViewHolder {

        RecyclerItemBinding binding;


        MainActivityFragmentRecyclerViewHolder(RecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Exercise exercise, Context context) {
            //Set an onClickListener on the delete icon to asynchronously delete the exercise
            binding.deleteIcon.setOnClickListener(view -> {
                new DeleteAsyncTask(exercise,context).execute();
            });

            //Edit Icon onClickListener
            binding.editIcon.setOnClickListener(
                    (view) ->{
                        int exerciseId=exercise.getId();
                        context.startActivity(new Intent(context, NewAddExerciseActivity.class).putExtra("exerciseId", exerciseId));
                    }
            );

            String exerciseName = exercise.getName();
            binding.exerciseTextView.setText(exerciseName);
            binding.executePendingBindings();
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        Exercise exercise;
        Context context;

        public DeleteAsyncTask(Exercise exercise, Context context) {
            this.context=context;
            this.exercise = exercise;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ExerciseDatabase.getInstance(context).exerciseDAO().deleteExercises(exercise);
            return null;
        }
    }
}
