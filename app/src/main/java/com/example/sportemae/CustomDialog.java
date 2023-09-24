package com.example.sportemae;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CustomDialog extends DialogFragment {

    ListView list;
    MaterialButton addBtn;
    LinearLayout view;
    Cursor workoutCursor;
    SimpleCursorAdapter workoutAdapter;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    int exercise;
    int day;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sqlHelper = new DatabaseHelper(getContext());
        //db = sqlHelper.getWritableDatabase();

        day = getArguments().getInt("day");
        exercise = getArguments().getInt("exercise");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        List<String> daysTable = new ArrayList<>();
        daysTable.add("monday");
        daysTable.add("tuesday");
        daysTable.add("wednesday");
        daysTable.add("thursday");
        daysTable.add("friday");
        daysTable.add("saturday");
        daysTable.add("sunday");

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        builder.setTitle("Добавить упражнение?");
        //builder.setMessage("ex: " + toString().valueOf(exercise) + " day: " + toString().valueOf(day));
        builder.setIcon(android.R.drawable.ic_input_get);
        view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog, null);
        builder.setView(view);
        addBtn = (MaterialButton) view.findViewById(R.id.addButton);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                databaseHelper = new DatabaseHelper(getContext());
                databaseHelper.create_db();
                db = databaseHelper.getWritableDatabase();
                db = databaseHelper.open();

                ContentValues cv = new ContentValues();
                cv.put("exerciseNumber", exercise);
                long newRowId = db.insert(daysTable.get(day-1), null, cv);

                // Выводим сообщение в успешном случае или при ошибке
                if (newRowId == -1) {
                    // Если ID  -1, значит произошла ошибка
                    Toast.makeText(getContext(), "Ошибка при добавлении", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Упражнение добавлено", Toast.LENGTH_SHORT).show();
                }

                goHome();
            }
        });


        databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.create_db();
        db = databaseHelper.open();
        workoutCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                DatabaseHelper.COLUMN_WID + "=?", new String[]{String.valueOf(toString().valueOf(exercise))});

        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.COLUMN_EX, DatabaseHelper.COLUMN_DESC};

        // создаем адаптер, передаем в него курсор
        workoutAdapter = new SimpleCursorAdapter(getContext(), android.R.layout.two_line_list_item,
                workoutCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);

        list = (ListView) view.findViewById(R.id.dialogList);
        list.setAdapter(workoutAdapter);
        return builder.create();
    }

    public static CustomDialog newInstance(int day, int exercise) {
        CustomDialog fragment = new CustomDialog();
        Bundle args = new Bundle();
        args.putInt("exercise", exercise);
        args.putInt("day", day);
        fragment.setArguments(args);
        return fragment;
    }

    private void goHome() {
        // закрываем подключение
        db.close();
    }
}