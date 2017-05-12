package com.example.dudar.deutschverben.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.dudar.deutschverben.data.duvContract.GroupEntry;
import com.example.dudar.deutschverben.data.duvContract.VerbEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dudar on 04.04.2017.
 */

public class duvDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = duvDbHelper.class.getSimpleName();
    Context context;

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "deutschverben.db";

    public duvDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_GROUPS_TABLE = "CREATE TABLE " + GroupEntry.TABLE_NAME + "(" +
                GroupEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GroupEntry.COLUMN_EINORDNUNG + " TEXT NOT NULL, "
                + GroupEntry.COLUMN_MNEMO + " TEXT, "
                + GroupEntry.COLUMN_GROUP + " INTEGER NOT NULL, "
                + GroupEntry.COLUMN_ANNOTATION + " TEXT);";

        db.execSQL(SQL_CREATE_GROUPS_TABLE);

        final String SQL_CREATE_VERBS_TABLE = "CREATE TABLE " + VerbEntry.TABLE_NAME + "(" +
                VerbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + VerbEntry.COLUMN_INFINITIV + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_PRAETERITUM + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_PERFEKT + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_HILFSVERB + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_GROUPID + " INTEGER NOT NULL, "
                + VerbEntry.COLUMN_TRANSLATION + " TEXT);";

        db.execSQL(SQL_CREATE_VERBS_TABLE);

        FillDataBase(db);
    }

    private void FillDataBase(SQLiteDatabase db) {

        String jsonStr = loadJSONFromAsset();

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array Groups node
                JSONArray groups = jsonObj.getJSONArray("groups");

                //looping through all groups
                for (int i = 0; i < groups.length(); i++) {
                    JSONObject g = groups.getJSONObject(i);

                    int id = g.getInt(GroupEntry.JSON_ID);
                    String einordnung = g.getString(GroupEntry.JSON_EINORDNUNG);
                    String mnemo = g.getString(GroupEntry.JSON_MNEMO);
                    int group = g.getInt(GroupEntry.JSON_GROUP);
                    String annotation = g.getString(GroupEntry.JSON_ANNOTATION);

                    ContentValues values = new ContentValues();
                    values.put(GroupEntry._ID, id);
                    values.put(GroupEntry.COLUMN_EINORDNUNG, einordnung);
                    values.put(GroupEntry.COLUMN_MNEMO, mnemo);
                    values.put(GroupEntry.COLUMN_GROUP, group);
                    values.put(GroupEntry.COLUMN_ANNOTATION, annotation);

                    db.insert(GroupEntry.TABLE_NAME, null, values);

                }
                //Getting JSON Array Verbs node
                JSONArray verbs = jsonObj.getJSONArray("verbs");

                //looping through all verbs
                for (int i = 0; i < verbs.length(); i++) {
                    JSONObject v = verbs.getJSONObject(i);

                    ContentValues verbValues = new ContentValues();
                    verbValues.put(VerbEntry._ID, v.getInt(VerbEntry.JSON_ID));
                    verbValues.put(VerbEntry.COLUMN_INFINITIV, v.getString(VerbEntry.JSON_INFINITIV));
                    verbValues.put(VerbEntry.COLUMN_PRAETERITUM, v.getString(VerbEntry.JSON_PRAETERITUM));
                    verbValues.put(VerbEntry.COLUMN_PERFEKT, v.getString(VerbEntry.JSON_PERFEKT));
                    verbValues.put(VerbEntry.COLUMN_HILFSVERB, v.getString(VerbEntry.JSON_HILFSVERB));
                    verbValues.put(VerbEntry.COLUMN_GROUPID, v.getInt(VerbEntry.JSON_GROUPID));
                    verbValues.put(VerbEntry.COLUMN_TRANSLATION, v.getString(VerbEntry.JSON_TRANSLATION));

                    db.insert(VerbEntry.TABLE_NAME, null, verbValues);
                    Log.i(LOG_TAG,verbValues.toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to do be done here.
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("duv.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private class GetVerbs extends AsyncTask<Void, Void, Void> {
        SQLiteDatabase db;

        GetVerbs(SQLiteDatabase database) {
            db = database;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context, "DB start to be created", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String jsonStr = loadJSONFromAsset();

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array Groups node
                    JSONArray groups = jsonObj.getJSONArray("groups");

                    //looping through all groups
                    for (int i = 0; i < groups.length(); i++) {
                        JSONObject g = groups.getJSONObject(i);

                        int id = g.getInt(GroupEntry.JSON_ID);
                        String einordnung = g.getString(GroupEntry.JSON_EINORDNUNG);
                        String mnemo = g.getString(GroupEntry.JSON_MNEMO);
                        int group = g.getInt(GroupEntry.JSON_GROUP);
                        String annotation = g.getString(GroupEntry.JSON_ANNOTATION);

                        ContentValues values = new ContentValues();
                        values.put(GroupEntry._ID, id);
                        values.put(GroupEntry.COLUMN_EINORDNUNG, einordnung);
                        values.put(GroupEntry.COLUMN_MNEMO, mnemo);
                        values.put(GroupEntry.COLUMN_GROUP, group);
                        values.put(GroupEntry.COLUMN_ANNOTATION, annotation);

                        db.insert(GroupEntry.TABLE_NAME, null, values);

                    }
                    //Getting JSON Array Verbs node
                    JSONArray verbs = jsonObj.getJSONArray("verbs");

                    //looping through all verbs
                    for (int i = 0; i < verbs.length(); i++) {
                        JSONObject v = verbs.getJSONObject(i);

                        ContentValues verbValues = new ContentValues();
                        verbValues.put(VerbEntry._ID, v.getInt(VerbEntry.JSON_ID));
                        verbValues.put(VerbEntry.COLUMN_INFINITIV, v.getString(VerbEntry.JSON_INFINITIV));
                        verbValues.put(VerbEntry.COLUMN_PRAETERITUM, v.getString(VerbEntry.JSON_PRAETERITUM));
                        verbValues.put(VerbEntry.COLUMN_PERFEKT, v.getString(VerbEntry.JSON_PERFEKT));
                        verbValues.put(VerbEntry.COLUMN_HILFSVERB, v.getString(VerbEntry.JSON_HILFSVERB));
                        verbValues.put(VerbEntry.COLUMN_GROUPID, v.getInt(VerbEntry.JSON_GROUPID));
                        verbValues.put(VerbEntry.COLUMN_TRANSLATION, v.getString(VerbEntry.JSON_TRANSLATION));

                        db.insert(VerbEntry.TABLE_NAME, null, verbValues);
                        Log.i(LOG_TAG,verbValues.toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context, "DB created", Toast.LENGTH_SHORT).show();
        }
    }
}
