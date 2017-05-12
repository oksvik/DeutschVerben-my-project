package com.example.dudar.deutschverben;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dudar.deutschverben.data.duvContract;

public class MainActivity extends AppCompatActivity {

    MyTask mt;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        mt = new MyTask();
        mt.execute();
   }


    class MyTask extends AsyncTask<Void, Void, Void> {

         @Override
        protected Void doInBackground(Void... voids) {

            String[] projection = {
                    duvContract.GroupEntry._ID
            };

            Cursor cursor = getContentResolver().query(duvContract.GroupEntry.CONTENT_URI,projection,null,null,null);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context, "DB created", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, StartActivity.class);
            startActivity(intent);
        }
    }
}
