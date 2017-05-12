package com.example.dudar.deutschverben;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.dudar.deutschverben.data.duvContract;

/**
 * Created by dudar on 21.04.2017.
 */

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    String[] projection = {
            duvContract.GroupEntry._ID,
            duvContract.GroupEntry.COLUMN_EINORDNUNG,
            duvContract.GroupEntry.COLUMN_GROUP
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        findViewById(R.id.btn_list).setOnClickListener(this);
        findViewById(R.id.btn_settings).setOnClickListener(this);

        Cursor cursor = getContentResolver().query(duvContract.GroupEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        String str = "";
        switch (view.getId()){
            case R.id.btn_list:
                str="Open verbs list";
                intent = new Intent(this,VerbsListActivity.class);
                break;
            case R.id.btn_settings:
                break;
        }
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
