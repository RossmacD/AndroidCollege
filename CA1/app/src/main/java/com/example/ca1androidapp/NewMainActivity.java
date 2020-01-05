package com.example.ca1androidapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.ca1androidapp.database.DatabaseInitializer;
import com.example.ca1androidapp.database.ExerciseDatabase;
import com.facebook.stetho.Stetho;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class NewMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_exercises, R.id.navigation_go)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        //Allows for database inspection,editing and debugging features from chrome - chrome:\\inspect
//        Stetho.initializeWithDefaults(this);

        // Populate the Database.
        DatabaseInitializer.populateAsync(ExerciseDatabase.getInstance(getApplicationContext()));

        // Set the layout and add toolbar
//        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        setSupportActionBar(binding.toolbar);



    }

}
