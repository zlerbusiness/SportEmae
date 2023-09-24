package com.example.sportemae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ExerciseActivity<string> extends AppCompatActivity {

    ListView workoutList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor workoutCursor;
    SimpleCursorAdapter workoutAdapter;
    String exMuscle = "";
    String pageNumber = "";
    String placeCheck = "В тренажёрном зале";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise);

        MaterialButton backbtn = (MaterialButton) findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), SecondActivity.class);
                startActivity(intent);
            }

        });

        /*MaterialButton muscleBtn = (MaterialButton) findViewById(R.id.muscleBtn);
        muscleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ThirdActivity.class);
                startActivity(intent);
            }

        });*/

        workoutList = findViewById(R.id.list);
        workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager = getSupportFragmentManager();
                CustomDialog myDialog = CustomDialog.newInstance(Integer.parseInt(pageNumber), (int) id);
                myDialog.show(manager, "dialog");
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pageNumber = extras.getString("pageNumber");
            exMuscle = extras.getString("muscle");
        }

        RadioGroup radGrp = (RadioGroup)findViewById(R.id.radioGroup);
        radGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                switch(id) {
                    case R.id.radio_home:
                        placeCheck = "Дома";
                        SetEx();
                        break;
                    case R.id.radio_gym:
                        placeCheck = "В тренажёрном зале";
                        SetEx();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void SetEx () {
        db = databaseHelper.open();

        //получаем данные из бд в виде курсора
        workoutCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                DatabaseHelper.COLUMN_MUSCLE + "=?" + " and " + DatabaseHelper.COLUMN_PLACE + "=\"" + placeCheck + "\"", new String[]{String.valueOf(exMuscle)});

        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.COLUMN_EX, DatabaseHelper.COLUMN_PLACE};

        // создаем адаптер, передаем в него курсор
        workoutAdapter = new SimpleCursorAdapter(this, R.layout.list_ex,
                workoutCursor, headers, new int[]{R.id.nameView}, 0);
        workoutList.setAdapter(workoutAdapter);
    }

    @Override
    public void onResume () {
        super.onResume();
        // открываем подключение
        SetEx();
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        // Закрываем подключение и курсор
        goHome();
        workoutCursor.close();
    }


    private void goHome() {
        // закрываем подключение
        db.close();
    }
}