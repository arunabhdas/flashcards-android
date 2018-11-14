package com.google.developer.flashcards;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.widget.TextView;

import com.google.developer.flashcards.data.DatabaseContract;

/**
 * Created by coder on 2/5/17.
 */

public class AnswerActivity extends AppCompatActivity {

    private TextView vQuestionTextView;
    private TextView vAnswerTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        vQuestionTextView = (TextView) findViewById(R.id.text_question);
        vAnswerTextView = (TextView) findViewById(R.id.text_answer);

    }

    @Override
    protected void onResume() {
        super.onResume();

        final String question = this.getIntent().getStringExtra(DatabaseContract.TableFlashcards.COL_QUESTION);
        final String answer = this.getIntent().getStringExtra(DatabaseContract.TableFlashcards.COL_ANSWER);

        vQuestionTextView.setText(question);
        vAnswerTextView.setText(answer);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }



}
