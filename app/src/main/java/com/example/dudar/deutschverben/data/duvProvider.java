package com.example.dudar.deutschverben.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.dudar.deutschverben.data.duvContract.GroupEntry;
import com.example.dudar.deutschverben.data.duvContract.VerbEntry;

/**
 * Created by dudar on 04.04.2017.
 */
public class duvProvider extends ContentProvider {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = duvProvider.class.getSimpleName();

    /**
     * Database helper object
     */
    duvDbHelper mDbHelper;

    /**
     * URI matcher code for the content URI for the groups table
     */
    private static final int GROUPS = 100;

    /**
     * URI matcher code for the content URI for a single group in the groups table
     */
    private static final int GROUPS_ID = 101;

    /**
     * URI matcher code for the content URI for the verbs table
     */
    private static final int VERBS = 200;

    /**
     * URI matcher code for the content URI for a single verb in the verbs table
     */
    private static final int VERBS_ID = 201;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(duvContract.CONTENT_AUTHORITY, duvContract.PATH_GROUPS, GROUPS);
        sUriMatcher.addURI(duvContract.CONTENT_AUTHORITY, duvContract.PATH_GROUPS + "/#", GROUPS_ID);
        sUriMatcher.addURI(duvContract.CONTENT_AUTHORITY, duvContract.PATH_VERBS, VERBS);
        sUriMatcher.addURI(duvContract.CONTENT_AUTHORITY, duvContract.PATH_VERBS + "/#", VERBS_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new duvDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Get readable database
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case GROUPS:
                cursor = db.query(GroupEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case GROUPS_ID:
                selection = GroupEntry._ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                cursor = db.query(GroupEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case VERBS:
                cursor = db.query(VerbEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case VERBS_ID:
                selection = VerbEntry._ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                cursor = db.query(VerbEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case GROUPS:
                return GroupEntry.CONTENT_LIST_TYPE;
            case GROUPS_ID:
                return GroupEntry.CONTENT_ITEM_TYPE;
            case VERBS:
                return VerbEntry.CONTENT_LIST_TYPE;
            case VERBS_ID:
                return VerbEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case GROUPS:
                return insertGroup(uri, contentValues);
            case VERBS:
                return insertVerb(uri,contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertVerb(Uri uri, ContentValues values) {
        //Get writable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long newRowId = db.insert(VerbEntry.TABLE_NAME, null, values);
        if (newRowId == -1)
            return null;

        // Notify all listeners that the data has changed for the verbs content URI
        getContext().getContentResolver().notifyChange(uri,null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, newRowId);
    }

    private Uri insertGroup(Uri uri, ContentValues values) {
        //Get writable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long newRowId = db.insert(GroupEntry.TABLE_NAME, null, values);
        if (newRowId == -1)
            return null;

        // Notify all listeners that the data has changed for the groups content URI
        getContext().getContentResolver().notifyChange(uri,null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, newRowId);
     }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
