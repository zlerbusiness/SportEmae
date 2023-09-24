package com.example.sportemae;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class listWithButtonAdapter extends SimpleCursorAdapter {

    LayoutInflater inflater;
    int layout;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    String TABLE;

    public listWithButtonAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags, String table) {
        super(context, layout, c, from, to, flags);
        this.TABLE = table;
        this.layout = layout;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        Button btn = (Button) view.findViewById(R.id.addButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper = new DatabaseHelper(view.getContext());
                databaseHelper.create_db();
                db = databaseHelper.getWritableDatabase();
                db = databaseHelper.open();

                View parent_row = (View) v.getParent();
                ListView lv = (ListView) parent_row.getParent();
                final int position = lv.getPositionForView(parent_row);

                db.delete(TABLE, "_id = " + getItemId(position), null);

                Toast.makeText(view.getContext(), "Упражнение удалено", Toast.LENGTH_SHORT).show();

                databaseHelper.close();
            }
        });
        super.bindView(view, context, cursor);
    }


    private class ViewHolder {
        final Button addButton;
        final TextView nameView;
        ViewHolder(@NonNull View view){
            addButton = view.findViewById(R.id.addButton);
            nameView = view.findViewById(R.id.nameView);
        }
    }
}