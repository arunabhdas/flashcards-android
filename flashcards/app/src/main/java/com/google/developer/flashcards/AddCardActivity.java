package com.google.developer.flashcards;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.developer.flashcards.data.AddCardService;
import com.google.developer.flashcards.data.DatabaseContract;

public class AddCardActivity extends AppCompatActivity {
    private TextInputEditText mTextInputQuestion;
    private TextInputEditText mTextInputAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        mTextInputQuestion = (TextInputEditText) findViewById(R.id.text_input_question);
        mTextInputAnswer = (TextInputEditText) findViewById(R.id.text_input_answer);

        mTextInputAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        String question = mTextInputQuestion.getText().toString().trim();
        String answer = mTextInputAnswer.getText().toString().trim();

        if (question.length() == 0) {
            mTextInputQuestion.setError(getString(R.string.error_input_question));
        }

        if (answer.length() == 0) {
            mTextInputAnswer.setError(getString(R.string.error_input_answer));
        }

        if (question.length() > 0 && answer.length() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.TableFlashcards.COL_QUESTION, question);
            contentValues.put(DatabaseContract.TableFlashcards.COL_ANSWER, answer);

            /*
            Uri uri = getContentResolver().insert(DatabaseContract.CONTENT_URI, contentValues);
            if (uri == null) {
                Toast.makeText(this, R.string.error_adding_card, Toast.LENGTH_LONG).show();
            } else {
                finish();
            }
            */

            AddCardService.insertNewCard(this, contentValues);
            finish();

        } else {
            Snackbar.make(findViewById(R.id.content),
                    R.string.complete_form, Snackbar.LENGTH_SHORT).show();
        }
    }
}
