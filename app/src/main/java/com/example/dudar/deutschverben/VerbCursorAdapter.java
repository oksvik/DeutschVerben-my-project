package com.example.dudar.deutschverben;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.dudar.deutschverben.data.duvContract.VerbEntry;

/**
 * Created by dudar on 21.04.2017.
 */

public class VerbCursorAdapter extends CursorAdapter {
    public VerbCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags*/);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find fields to populate in inflated template
        TextView tvInf = (TextView) view.findViewById(R.id.word_infinitiv);
        TextView tvPast = (TextView) view.findViewById(R.id.word_past);
        TextView tvPerf = (TextView) view.findViewById(R.id.word_perfect);
        TextView tvTrans = (TextView)view.findViewById(R.id.word_translation);
        // Extract properties from cursor
        String sInf = cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_INFINITIV));
        String sPast = cursor.getString(cursor.getColumnIndexOrThrow(VerbEntry.COLUMN_PRAETERITUM));
        String sPerf = cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_PERFEKT));
        String sTrans = cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_TRANSLATION));

        // Populate fields with extracted properties
        tvInf.setText(sInf);
        tvPast.setText(sPast);
        tvPerf.setText(sPerf);
        tvTrans.setText(sTrans);
    }
}
