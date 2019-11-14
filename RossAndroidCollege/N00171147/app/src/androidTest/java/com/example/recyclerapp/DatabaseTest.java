package com.example.recyclerapp;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.recyclerapp.database.AppDatabase;
import com.example.recyclerapp.database.NoteDao;
import com.example.recyclerapp.database.NoteEntity;
import com.example.recyclerapp.utilities.SampleData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.security.auth.login.LoginException;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private NoteDao mDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        //mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();

        mDao = mDb.noteDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void  closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }
    @Test
    public void createAndRetrieveNotes () {
        mDao.insertAll(SampleData.getNotes());
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveNotes: count=" + count);
        assertEquals(SampleData.getNotes().size(), count);
    }

    @Test
    public void compareStrings() {
        mDao.insertAll(SampleData.getNotes());
        NoteEntity original = SampleData.getNotes().get(0);
        NoteEntity fromDb = mDao.getNoteById(1);
        assertEquals(original.getText(), fromDb.getText());
        assertEquals(1, fromDb.getId());
    }


}
