package com.example.ttscoach;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.ttscoach.database.ExercisesViewModel;
import com.example.ttscoach.databinding.FragmentMainBinding;


import java.util.ArrayList;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        //Create bindings for the main fragment
        FragmentMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        //Initialise the recyclerview adapter
        MainActivityFragmentRecyclerViewAdapter recyclerViewAdapter = new MainActivityFragmentRecyclerViewAdapter(new ArrayList<>(),getActivity().getApplication());

        //Set the layout manger for the
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Attach  the adapter to the recycler view
        binding.recyclerView.setAdapter(recyclerViewAdapter);


        // When the Floating Action Button is clicked start the Add Exercise Activity.
        binding.fab.setOnClickListener((view) ->{
                    startActivity(new Intent(getActivity(), AddExerciseActivity.class));
                }
        );

        //
        ExercisesViewModel viewModel = ViewModelProviders.of(this).get(ExercisesViewModel.class);
        //Live Data is shown in the fragment - it updates the ui if the database is updated
        viewModel.getExercise().observe(MainActivityFragment.this, recyclerViewAdapter::setExercises);
        return binding.getRoot();
    }
}
