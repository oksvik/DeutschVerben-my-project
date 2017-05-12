package com.example.dudar.deutschverben;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import static com.example.dudar.deutschverben.data.duvContract.VerbEntry;

/**
 * Created by dudar on 06.04.2017.
 */
public class VerbsListActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        String[] projection = {
                VerbEntry._ID,
                VerbEntry.COLUMN_INFINITIV,
                VerbEntry.COLUMN_PRAETERITUM,
                VerbEntry.COLUMN_PERFEKT,
                VerbEntry.COLUMN_TRANSLATION
        };
        Cursor cursor = getContentResolver().query(VerbEntry.CONTENT_URI, projection, null, null, null);

        VerbCursorAdapter verbAdapter = new VerbCursorAdapter(this,cursor);

        ListView verbListView = (ListView) findViewById(R.id.wordlist);
        verbListView.setAdapter(verbAdapter);

        verbListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
            }
        });

  }

}
