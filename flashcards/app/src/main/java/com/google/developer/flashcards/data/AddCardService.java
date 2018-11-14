package com.google.developer.flashcards.data;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* Process DB insert on a background thread */
public class AddCardService extends IntentService {
    private static final String TAG = AddCardService.class.getSimpleName();

    public static final String ACTION_INSERT = TAG + ".INSERT";

    public static final String EXTRA_VALUES = TAG + ".ContentValues";

    public static void insertNewCard(Context context, ContentValues values) {
        Intent intent = new Intent(context, AddCardService.class);
        intent.setAction(ACTION_INSERT);
        intent.putExtra(EXTRA_VALUES, values);
        context.startService(intent);
    }

    public AddCardService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ACTION_INSERT.equals(intent.getAction())) {
            ContentValues values = intent.getParcelableExtra(EXTRA_VALUES);
            performInsert(values);
        }
    }

    private void performInsert(ContentValues values) {
        if (getContentResolver().insert(DatabaseContract.CONTENT_URI, values) != null) {
            Log.d(TAG, "Inserted new card");
        } else {
            Log.w(TAG, "Error inserting new card");
        }
    }
}
