package com.example.ca1androidapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ca1androidapp.database.Exercise;

import java.util.List;
import androidx.databinding.DataBindingUtil;

public class MainActivityFragmentRecyclerViewAdapter extends RecyclerView.Adapter<MainActivityFragmentRecyclerViewAdapter.MainActivityFragmentRecyclerViewHolder> {

    private List<Exercise> exercises;

    public MainActivityFragmentRecyclerViewAdapter(List<Exercise> Exercises) {
        this.exercises = exercises;
    }

    @Override
    public MainActivityFragmentRecyclerViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        RecyclerItemBinding itemBinding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new MainActivityFragmentRecyclerViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(
            MainActivityFragmentRecyclerViewHolder holder, int position) {
        String exerciseName = exercises.get(position).getExerciseName();
        holder.bind(exerciseName);
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

        void bind(String exerciseName) {
            binding.exerciseTextView.setText(exerciseName);
            binding.executePendingBindings();
        }
    }
}
