package com.google.developer.flashcards.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This class contains the public contract for the database and
 * content provider. No changes should be made to the code here.
 */
public class DatabaseContract {

    // Database schema information
    public static final String TABLE_FLASHCARDS = "flashcards";
    public static final class TableFlashcards implements BaseColumns {
        public static final String COL_ID = "_id";
        public static final String COL_QUESTION = "question";
        public static final String COL_ANSWER = "answer";
    }

    // Unique authority string for the content provider
    public static final String CONTENT_AUTHORITY = "com.google.developer.flashcards";
    // Default sort for query results
    public static final String DEFAULT_SORT_FLASHCARDS = TableFlashcards.COL_ID;

    // Base content Uri for accessing the provider
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_FLASHCARDS)
            .build();
}
