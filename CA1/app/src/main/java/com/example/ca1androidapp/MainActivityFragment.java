package com.example.ca1androidapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.ca1androidapp.database.ExerciseViewModel;

import java.util.ArrayList;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        FragmentMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        MainActivityFragmentRecyclerViewAdapter recyclerViewAdapter = new MainActivityFragmentRecyclerViewAdapter(new ArrayList<>());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.recyclerView.setAdapter(recyclerViewAdapter);

        ExerciseViewModel viewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);

        viewModel.getExercise().observe(MainActivityFragment.this, recyclerViewAdapter::setExercises);

        return binding.getRoot();
    }
}
