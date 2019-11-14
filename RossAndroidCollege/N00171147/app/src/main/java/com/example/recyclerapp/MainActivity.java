package com.example.recyclerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import com.example.recyclerapp.ViewModel.MainViewModel;
import com.example.recyclerapp.database.NoteEntity;
import com.example.recyclerapp.ui.NotesAdapter;
import com.example.recyclerapp.utilities.SampleData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.floatingActionButton)
    void fabClickHandler(){
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private List<NoteEntity> notesData = new ArrayList<>();
    private NotesAdapter mAdapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initViewModel();
        initRecycleView();


        notesData.addAll(SampleData.getNotes());
        for(NoteEntity note:
            notesData
        ){
            Log.i("PlainOldNotes",note.toString());
        }
    }

    private void initViewModel() {
        mViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
    }

    private void initRecycleView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new NotesAdapter(notesData,this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
