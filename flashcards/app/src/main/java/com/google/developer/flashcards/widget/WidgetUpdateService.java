package com.google.developer.flashcards.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.developer.flashcards.R;
import com.google.developer.flashcards.data.DatabaseContract;
import com.google.developer.flashcards.data.Flashcard;

import java.util.Random;

public class WidgetUpdateService extends IntentService {
    private static final String TAG = WidgetUpdateService.class.getSimpleName();

    public static final String EXTRA_WIDGET_IDS = "widget_ids";

    public WidgetUpdateService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                FlashcardWidget.class));

        for (int id : appWidgetIds) {

            int totalCards = 0;
            String projection[] = new String[]  { DatabaseContract.TableFlashcards.COL_QUESTION, DatabaseContract.TableFlashcards.COL_ANSWER };
            Cursor cursor = getContentResolver().query(DatabaseContract.CONTENT_URI,
                    projection,
                    null,
                    null,
                    DatabaseContract.DEFAULT_SORT_FLASHCARDS);

            if (cursor != null) {
                totalCards = cursor.getCount();
            }

            if (cursor == null) {
                Log.w(TAG, "Unable to read card database");
                return;
            }

            Random randomNumberGenerator = new Random();
            int randomNumber = randomNumberGenerator.nextInt(totalCards);

            cursor.moveToPosition(randomNumber);
            Flashcard randomFlashcard = new Flashcard(cursor);

            //Load question info into widget view
            RemoteViews view = new RemoteViews(getPackageName(), R.layout.flashcard_widget);

            view.setTextViewText(R.id.appwidget_text, "Hello, World!");

            //Update the widget
            appWidgetManager.updateAppWidget(id, view);
        }
    }
}

