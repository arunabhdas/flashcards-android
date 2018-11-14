package com.google.developer.flashcards.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.developer.flashcards.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CardsDBHelper extends SQLiteOpenHelper {
    private static final String TAG = CardsDBHelper.class.getSimpleName();

    public static final String DB_NAME = "flashcards.db";
    public static final int DB_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FLASHCARDS = "CREATE TABLE " +
            DatabaseContract.TABLE_FLASHCARDS + " (" +
            DatabaseContract.TableFlashcards._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.TableFlashcards.COL_QUESTION + " TEXT NOT NULL," +
            DatabaseContract.TableFlashcards.COL_ANSWER + " TEXT NOT NULL )";

    private Resources mResources;

    public CardsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        mResources = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FLASHCARDS);
        try {
            readFlashcardsFromResources(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FLASHCARDS);
        onCreate(db);
    }

    private void readFlashcardsFromResources(SQLiteDatabase db) throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.flashcards);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        //Parse resource into key/values
        JSONObject root = new JSONObject(builder.toString());
        JSONArray flashcards = root.getJSONArray(Flashcard.KEY_FLASHCARDS);
        //Add each element to the database
        for (int i = 0; i < flashcards.length(); i++) {
            JSONObject item = flashcards.getJSONObject(i);
            ContentValues values = new ContentValues(2);

            values.put(DatabaseContract.TableFlashcards.COL_QUESTION,
                    item.getString(Flashcard.KEY_QUESTION));

            values.put(DatabaseContract.TableFlashcards.COL_ANSWER,
                    item.getString(Flashcard.KEY_ANSWER));

            db.insert(DatabaseContract.TABLE_FLASHCARDS, null, values);
        }
    }

    public List<Flashcard> getAllFlashcards()  {
        List<Flashcard> flashcards = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseContract.TABLE_FLASHCARDS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            do {
                Flashcard flashcard = new Flashcard();
                flashcard.setId(cursor.getLong(cursor.getColumnIndex(DatabaseContract.TableFlashcards._ID)));
                flashcard.setQuestion(cursor.getString(cursor.getColumnIndex(DatabaseContract.TableFlashcards.COL_QUESTION)));
                flashcard.setAnswer(cursor.getString(cursor.getColumnIndex(DatabaseContract.TableFlashcards.COL_ANSWER)));
                flashcards.add(flashcard);
            } while (cursor.moveToNext());
        }
        return flashcards;

    }
    public long addFlashcard(ContentValues values) throws SQLException {
        long id = getWritableDatabase().insert(DatabaseContract.TABLE_FLASHCARDS, "", values);
        if (id <= 0) {
            throw new SQLException("Failed to add flashcard");
        } else {
            return id;
        }
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
