package com.example.ttscoach;


import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

import com.example.ttscoach.database.Exercise;
import com.example.ttscoach.database.ExercisesViewModel;
import com.example.ttscoach.databinding.FragmentGoBinding;

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
    public GoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        speaking=false;

        ExercisesViewModel viewModel = ViewModelProviders.of(this).get(ExercisesViewModel.class);

        //Live Data is shown in the fragment
        viewModel.getExercise().observe(getActivity(), this::setExercises);


        //get handle to main loop
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
        //Kill Thread tasks
        stopped=true;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        if(getActivity()!=null){
            startSession();
        }
    }

    public void startSession(){
        //Get views
        nameText=getActivity().findViewById(R.id.nameText);
        countText=getActivity().findViewById(R.id.countText);
        goText=getActivity().findViewById(R.id.goText);
        setText=getActivity().findViewById(R.id.setText);
        progressBar=getActivity().findViewById(R.id.progressBar);
        stopButton=getActivity().findViewById(R.id.stopButton);
        //Kill switch
        stopButton.setOnClickListener((view) -> {
                stopped=true;
            }
        );
        prepareTTS();
        speaking=true;
        stopped=false;

        new Thread(() -> {
            while (speaking && !stopped){
                //Do nothing - wait till talking has stopped
            }
            for(Exercise exercise : exercises){
                ConvertTextToSpeech("Now starting"+exercise.getName());
                while (speaking && !stopped){
                    //Do nothing - wait till talking has stopped
                }
                //Update UI thread
                handler.post(() -> {
                    goText.setText("GO!");
                    countText.setBackgroundResource(R.drawable.circle);
                });
                for(int setCount=1;setCount<=exercise.getSets();setCount++) {
                    //Check if the loop should be killed
                    if(stopped){
                        break;
                    }
                    final int  finalSetCount=setCount;
                    ConvertTextToSpeech("Set "+setCount+", GO!");

                    while (speaking && !stopped){
                        //Do nothing - wait till speaking has stopped
                    }
                    //Make changes in UI thread
                    handler.post(() -> {
                        goText.setText("GO!");
                        countText.setBackgroundResource(R.drawable.circle);
                    });
                    for (int repCount = 1; repCount <= exercise.getReps(); repCount++) {
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

    /**
     * Initialise TTS & add listener
     */
    private void prepareTTS(){
        tts=new TextToSpeech(getActivity(), status -> {
            if(status == TextToSpeech.SUCCESS){
                Log.e("RossTTs", "success");
                //Listener to see if TTS is still speaking
                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    //If TTS is Done
                    @Override
                    public void onDone(String utteranceId) {
                        speaking=false;
                        Log.e("RossTTs", "Done");
                    }
                    //If TTS has an error
                    @Override
                    public void onError(String utteranceId) {
                        speaking=false;
                    }
                    //Runs when TTS is started
                    @Override
                    public void onStart(String utteranceId) {
                        speaking=true;
                    }
                });
                //Set the TTS language
                int result=tts.setLanguage(Locale.US);
                //If there is an error loading the language
                if(result==TextToSpeech.LANG_MISSING_DATA ||
                        result==TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("RossTTs", "This Language is not supported");
                }
                else{
                    //Run first  TTS Command
                    ConvertTextToSpeech("Lets Begin");
                }
            }
            else
                //Error Catch
                Log.e("RossTTs", "Initilization Failed!");
        });
    }

    private void ConvertTextToSpeech(String s) {
        speaking=true;
        //The string is put into a hash map and queued - ID is irrelevent but needed for the listener
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, map);
    }
}
