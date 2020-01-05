package com.example.ca1androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.ca1androidapp.database.ExercisesViewModel;
import com.example.ca1androidapp.databinding.FragmentMainBinding;


import java.util.ArrayList;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        //Create bindings for the main fragment
        FragmentMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        //
        MainActivityFragmentRecyclerViewAdapter recyclerViewAdapter = new MainActivityFragmentRecyclerViewAdapter(new ArrayList<>(),getActivity().getApplication());


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.recyclerView.setAdapter(recyclerViewAdapter);


// When the Floating Action Button is clicked start the Add Exercise Activity.
        binding.fab.setOnClickListener((view) ->{
                    startActivity(new Intent(getActivity(), AddExerciseActivity.class));
                }
        );

        //
        ExercisesViewModel viewModel = ViewModelProviders.of(this).get(ExercisesViewModel.class);

        //Live Data is shown in the fragment
        viewModel.getExercise().observe(MainActivityFragment.this, recyclerViewAdapter::setExercises);

        return binding.getRoot();
    }
}
