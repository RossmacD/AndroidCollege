package com.example.ca1androidapp;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ca1androidapp.database.Exercise;
import com.example.ca1androidapp.database.ExerciseDatabase;
import com.example.ca1androidapp.database.ExercisesViewModel;

import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoFragment extends Fragment {
    private List<Exercise> exercises;
    private TextView goText;
    private Button stopButton;
    private Boolean stopped;
    TextToSpeech tts;
    Handler handler;

    public GoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //exercises = ExerciseDatabase.getInstance(getActivity().getApplication()).exerciseDAO().getAllExercisesSync();
        ExercisesViewModel viewModel = ViewModelProviders.of(this).get(ExercisesViewModel.class);

        //Live Data is shown in the fragment
        viewModel.getExercise().observe(getActivity(), this::setExercises);


        //
        handler=new Handler(Looper.getMainLooper());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_go, container, false);
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        startSession();
    }

    public void startSession(){
        goText=getActivity().findViewById(R.id.goText);
        stopButton=getActivity().findViewById(R.id.stopButton);
        stopButton.setOnClickListener((view) -> {
                stopped=true;

            }
        );
        prepareTTS();
        stopped=false;
     //  new UiQueue().execute();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(Exercise exercise : exercises){
                    Log.d("rossi","Exercise Name= "+exercise.getName());
                    Log.d("rossi","Exercise reps= "+exercise.getReps());
                    Log.d("rossi","Exercise sets= "+exercise.getSets());
                    for(int setCount=1;setCount<=exercise.getSets();setCount++) {
                        Log.d("rossi","Exercise set= "+setCount);
                        for (int repCount = 1; repCount <= exercise.getReps(); repCount++) {
                            Log.d("rossi", "Rep " + repCount);
                            //Stop task when button pressed
                            if(stopped){
                                break;
                            }
                            //Set vars for passing into ui thread
                            final int finalRepCount=repCount, finalSetCount=setCount;
                            handler.post(() -> {
                                goText.setText(exercise.getName()+" | "+finalRepCount+" | "+finalSetCount);
                                ConvertTextToSpeech("Hello Lads");
                            });

                            //Wait
                            try {
                                Thread.sleep(exercise.getInterval()*1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }).start();


    }

    private void prepareTTS(){
        tts=new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    Log.e("RossTTs", "He,,k");
                    int result=tts.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("RossTTs", "This Language is not supported");
                    }
                    else{
                        ConvertTextToSpeech("Ready to speak, hello Ivan");
                    }
                }
                else
                    Log.e("RossTTs", "Initilization Failed!");
            }
        });
    }
    private void ConvertTextToSpeech(String s) {
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }

    //An async task to run the queue without blocking the main ui thread
    private class UiQueue extends AsyncTask<Void, Void, Void> {

        public UiQueue() {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            for(Exercise exercise : exercises){
                Log.d("rossi","Exercise Name= "+exercise.getName());
                Log.d("rossi","Exercise reps= "+exercise.getReps());
                Log.d("rossi","Exercise sets= "+exercise.getSets());
                for(int setCount=1;setCount<=exercise.getSets();setCount++) {
                    Log.d("rossi","Exercise set= "+setCount);
                    for (int repCount = 1; repCount <= exercise.getReps(); repCount++) {
                        Log.d("rossi", "Rep " + repCount);
                        //Stop task when button pressed
                        if(stopped){
                            return null;
                        }
                        //Set vars for passing into ui thread
                        final int finalRepCount=repCount, finalSetCount=setCount;
                        handler.post(() -> {
                            goText.setText(exercise.getName()+" | "+finalRepCount+" | "+finalSetCount);
                        });

                        //Wait
                        try {
                            Thread.sleep(exercise.getInterval()*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return null;
        }
    }
}
