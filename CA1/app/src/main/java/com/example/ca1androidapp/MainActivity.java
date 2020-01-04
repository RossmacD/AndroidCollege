package com.example.ca1androidapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.ca1androidapp.database.DatabaseInitializer;
import com.example.ca1androidapp.database.ExerciseDatabase;
import com.example.ca1androidapp.databinding.ActivityMainBinding;
import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Allows for database inspection,editing and debugging features from chrome - chrome:\\inspect
        Stetho.initializeWithDefaults(this);

        // Populate the Database.
        DatabaseInitializer.populateAsync(ExerciseDatabase.getInstance(getApplicationContext()));

        // Set the layout and add toolbar
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        // When the Floating Action Button is clicked start the Add Exercise Activity.
      binding.fab.setOnClickListener((view) ->{
                startActivity(new Intent(MainActivity.this, AddExerciseActivity.class));
      }
      );

    }
}
