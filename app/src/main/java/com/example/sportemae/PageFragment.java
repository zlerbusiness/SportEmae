package com.example.sportemae;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class PageFragment extends Fragment {

    ListView workoutList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor workoutCursor;
    SimpleCursorAdapter workoutAdapter;
    private int pageNumber;

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    public PageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;

        databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.create_db();
        db = databaseHelper.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_page, container, false);
        workoutList = result.findViewById(R.id.list);

        MaterialButton addbtn = (MaterialButton) result.findViewById(R.id.addEx);
        MaterialButton backbtn = (MaterialButton) result.findViewById(R.id.backbtn);
        MaterialButton refresh = (MaterialButton) result.findViewById(R.id.refresh);

        SetEx();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String header = "" + (pageNumber+1);
                Intent intent = new Intent(v.getContext(), ThirdActivity.class);
                intent.putExtra("pageNumber", header);
                startActivity(intent);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetEx();
            }
        });
        return result;
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        // Закрываем подключение и курсор
        goHome();
        workoutCursor.close();
    }

    public void SetEx () {
        //select name from tab_1 join tab_2 on tab_2.ex = tab_1.id

        List<String> daysTable = new ArrayList<>();
        daysTable.add("monday");
        daysTable.add("tuesday");
        daysTable.add("wednesday");
        daysTable.add("thursday");
        daysTable.add("friday");
        daysTable.add("saturday");
        daysTable.add("sunday");

        workoutCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " join " + daysTable.get(pageNumber) + " on " + daysTable.get(pageNumber) + ".exerciseNumber" + " = " + DatabaseHelper.TABLE + "._id", null);


        // формируем столбцы сопоставления
        String[] from = new String[] { DatabaseHelper.COLUMN_EX };
        int[] to = new int[] { R.id.nameView };

        // создаем адаптер и настраиваем список
        workoutAdapter = new listWithButtonAdapter(getContext(), R.layout.list_item, workoutCursor, from, to, 0, daysTable.get(pageNumber));
        workoutList.setAdapter(workoutAdapter);
    }


    private void goHome() {
        // закрываем подключение
        db.close();
    }
}