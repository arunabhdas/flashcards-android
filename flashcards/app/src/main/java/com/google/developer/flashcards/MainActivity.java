package com.google.developer.flashcards;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.developer.flashcards.adapter.MyCardRecyclerAdapter;
import com.google.developer.flashcards.adapter.RecyclerItemClickListener;
import com.google.developer.flashcards.data.CardsDBHelper;
import com.google.developer.flashcards.data.DatabaseContract;
import com.google.developer.flashcards.data.Flashcard;
import com.google.developer.flashcards.reminders.NotificationService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Flashcard> mFlashcards = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private MyCardRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    CardsDBHelper mDbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager cardLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(cardLayoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // TODO Handle item click
                Flashcard flashcard = mFlashcards.get(position);

                Intent intent = new Intent(MainActivity.this, AnswerActivity.class);
                intent.putExtra(DatabaseContract.TableFlashcards.COL_QUESTION, flashcard.getQuestion());
                intent.putExtra(DatabaseContract.TableFlashcards.COL_ANSWER, flashcard.getAnswer());
                startActivity(intent);
            }
        }));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);


        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        initDailyReminder();

    }

    protected void onResume() {
        super.onResume();
        mFlashcards.clear();
        mDbHelper = new CardsDBHelper(this);
        mFlashcards = mDbHelper.getAllFlashcards();
        mAdapter = new MyCardRecyclerAdapter(mFlashcards);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setMotionEventSplittingEnabled(false);

        mAdapter.notifyDataSetChanged();
        final int number = mFlashcards.size();
    }

    private void initDailyReminder() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String remindersKey = getString(R.string.pref_key_reminders);
        boolean enabled = sharedPreferences.getBoolean(remindersKey, false);

        if (enabled) {

            Intent myIntent = new Intent(this , NotificationService.class);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 35);
            calendar.set(Calendar.HOUR, 12);
            calendar.set(Calendar.AM_PM, Calendar.PM);
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

    }

    /* FAB Click Events */
    @Override
    public void onClick(View v) {
        Intent addCard = new Intent(this, AddCardActivity.class);
        MainActivity.this.startActivity(addCard);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
