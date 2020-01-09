package com.example.ca1androidapp;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ca1androidapp.database.Exercise;
import com.example.ca1androidapp.database.ExerciseDatabase;
import com.example.ca1androidapp.database.ExercisesViewModel;
import com.example.ca1androidapp.databinding.FragmentGoBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoFragment extends Fragment {
    private List<Exercise> exercises;
    private TextView goText,nameText,countText,setText;
    private ProgressBar progressBar;
    private Button stopButton;
    private Boolean stopped, speaking;
    TextToSpeech tts;
    Handler handler;
    //FragmentGoBinding binding;

    public GoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Generate binding
        //binding = DataBindingUtil.inflate(inflater, R.layout.fragment_go, container, false);
        speaking=false;

        //getActivity().getActionBar().hide();
        //exercises = ExerciseDatabase.getInstance(getActivity().getApplication()).exerciseDAO().getAllExercisesSync();
        ExercisesViewModel viewModel = ViewModelProviders.of(this).get(ExercisesViewModel.class);

        //Live Data is shown in the fragment
        viewModel.getExercise().observe(getActivity(), this::setExercises);


        //
        handler=new Handler(Looper.getMainLooper());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_go, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Hide Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        //Add Tool bar back in
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        //Kill Async tasks
        stopped=true;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        if(getActivity()!=null){
            startSession();
        }
    }

    public void startSession(){
        nameText=getActivity().findViewById(R.id.nameText);
        countText=getActivity().findViewById(R.id.countText);
        goText=getActivity().findViewById(R.id.goText);
        setText=getActivity().findViewById(R.id.setText);
        progressBar=getActivity().findViewById(R.id.progressBar);
        stopButton=getActivity().findViewById(R.id.stopButton);
        stopButton.setOnClickListener((view) -> {
                stopped=true;

            }

        );
        prepareTTS();
        speaking=true;
        stopped=false;
     //  new UiQueue().execute();
        new Thread(() -> {
            while (speaking && !stopped){
                //Do nothing
            }
            for(Exercise exercise : exercises){

                ConvertTextToSpeech("Now starting"+exercise.getName());
                while (speaking && !stopped){
                    //Do nothing
                }
                handler.post(() -> {
                    goText.setText("GO!");
                    countText.setBackgroundResource(R.drawable.circle);
                });
                Log.d("rossi","Speaking "+speaking);

               /* Log.d("rossi","Exercise Name= "+exercise.getName());
                Log.d("rossi","Exercise reps= "+exercise.getReps());
                Log.d("rossi","Exercise sets= "+exercise.getSets());*/
                for(int setCount=1;setCount<=exercise.getSets();setCount++) {
                    if(stopped){
                        break;
                    }
                   //Log.d("rossi","Exercise set= "+setCount);
                    final int  finalSetCount=setCount;
                    ConvertTextToSpeech("Set "+setCount+", GO!");

                    while (speaking && !stopped){
                        //Do nothing
                    }
                    handler.post(() -> {
                        goText.setText("GO!");

                        countText.setBackgroundResource(R.drawable.circle);
                    });
                    for (int repCount = 1; repCount <= exercise.getReps(); repCount++) {
                        /*Log.d("rossi", "Rep " + repCount);*/
                        //Stop task when button pressed
                        if(stopped){
                            break;
                        }

                        //Set vars for passing into ui thread
                        final int finalRepCount=repCount;
                        handler.post(() -> {
                            nameText.setText(""+exercise.getName());
                            countText.setText(""+finalRepCount);
                            //Set Progress value, animate if version allows it to
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                progressBar.setProgress( Math.round((finalRepCount*100)/exercise.getReps()),true);
                            }else{
                                progressBar.setProgress( Math.round((finalRepCount*100)/exercise.getReps()));
                            }
                            Log.d("rossi", "progress " + (finalRepCount*100)/exercise.getReps());
                            setText.setText("Set: "+finalSetCount+"/"+exercise.getSets());
                            ConvertTextToSpeech(""+finalRepCount);
                        });

                        //Wait
                        try {
                            Thread.sleep(exercise.getInterval()*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.post(() -> {
                        goText.setText("Break");
                        countText.setText(""+exercise.getSetBreak());
                        countText.setBackgroundResource(R.drawable.circle_red);
                    });
                    if(stopped){
                        break;
                    }
                    ConvertTextToSpeech("Set complete,Take a "+exercise.getSetBreak()+" seconds break!");
                    //Wait
                    try {
                        Thread.sleep(exercise.getSetBreak()*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            ConvertTextToSpeech("Exercise complete, well done");

        }).start();


    }

    private void prepareTTS(){


        tts=new TextToSpeech(getActivity(), status -> {
            if(status == TextToSpeech.SUCCESS){
                Log.e("RossTTs", "success");
                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onDone(String utteranceId) {
                        speaking=false;
                        Log.e("RossTTs", "Done");
                    }
                    @Override
                    public void onError(String utteranceId) {
                        speaking=false;
                        Log.e("RossTTs", "Error");
                    }
                    @Override
                    public void onStart(String utteranceId) {
                        speaking=true;
                        Log.e("RossTTs", "Started");
                    }
                });
                int result=tts.setLanguage(Locale.US);
                if(result==TextToSpeech.LANG_MISSING_DATA ||
                        result==TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("RossTTs", "This Language is not supported");
                }
                else{
                    ConvertTextToSpeech("Lets Begin");
                }
            }
            else
                Log.e("RossTTs", "Initilization Failed!");
        });


    }



    private void ConvertTextToSpeech(String s) {
        speaking=true;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, map);
    }

    //An async task to run the queue without blocking the main ui thread
//    private class UiQueue extends AsyncTask<Void, Void, Void> {
//
//        public UiQueue() {
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            for(Exercise exercise : exercises){
//                Log.d("rossi","Exercise Name= "+exercise.getName());
//                Log.d("rossi","Exercise reps= "+exercise.getReps());
//                Log.d("rossi","Exercise sets= "+exercise.getSets());
//                for(int setCount=1;setCount<=exercise.getSets();setCount++) {
//                    Log.d("rossi","Exercise set= "+setCount);
//                    for (int repCount = 1; repCount <= exercise.getReps(); repCount++) {
//                        Log.d("rossi", "Rep " + repCount);
//                        //Stop task when button pressed
//                        if(stopped){
//                            return null;
//                        }
//                        //Set vars for passing into ui thread
//                        final int finalRepCount=repCount, finalSetCount=setCount;
//                        handler.post(() -> {
//                            goText.setText(exercise.getName()+" | "+finalRepCount+" | "+finalSetCount);
//                        });
//
//                        //Wait
//                        try {
//                            Thread.sleep(exercise.getInterval()*1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//            return null;
//        }
//    }
}
