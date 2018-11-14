package com.google.developer.flashcards.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class CardsProvider extends ContentProvider {
    private static final String TAG = CardsProvider.class.getSimpleName();

    private static final int FLASHCARDS = 200;
    private static final int FLASHCARDS_WITH_ID = 201;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_FLASHCARDS,
                FLASHCARDS);

        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_FLASHCARDS + "/#",
                FLASHCARDS_WITH_ID);
    }

    private CardsDBHelper mCardsDBHelper = null;

    @Override
    public boolean onCreate() {
        mCardsDBHelper = new CardsDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        //TODO: Implement the query function
        SQLiteDatabase db = mCardsDBHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        boolean useAuthorityUri = false;
        switch (sUriMatcher.match(uri)) {
            case FLASHCARDS_WITH_ID:
               queryBuilder.appendWhere(DatabaseContract.TableFlashcards.COL_ID + "=" + uri.getLastPathSegment());
                break;

            case FLASHCARDS:
                // no filter
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //TODO: Implement the insert function
        try {
            long id = mCardsDBHelper.addFlashcard(values);
            Uri returnUri = ContentUris.withAppendedId(DatabaseContract.CONTENT_URI, id);
            return returnUri;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("This provider does not support deletion");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("This provider does not support updates");
    }
}
