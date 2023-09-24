package com.example.sportemae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class ThirdActivity extends AppCompatActivity {

    CardView chest;
    CardView triceps;
    CardView back;
    CardView biceps;
    CardView legs;
    CardView should;
    String muscle;
    String pageNumber = "";
    String header;
    TextView textTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        textTest = findViewById(R.id.choosegroup);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pageNumber = extras.getString("pageNumber");
        }

        header = "" + (pageNumber);
        //textTest.setText(header);

        MaterialButton backbtn = (MaterialButton) findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), SecondActivity.class);
                startActivity(intent);
            }

        });

        chest = findViewById(R.id.chest);
        triceps = findViewById(R.id.triceps);
        back = findViewById(R.id.back);
        biceps = findViewById(R.id.biceps);
        legs = findViewById(R.id.legs);
        should = findViewById(R.id.should);

        chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muscle = "Chest";
                Intent intent = new Intent(v.getContext(), ExerciseActivity.class);
                intent.putExtra("pageNumber", header);
                intent.putExtra("muscle", muscle);
                startActivity(intent);
            }
        });

        triceps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muscle = "Triceps";
                Intent intent = new Intent(v.getContext(), ExerciseActivity.class);
                intent.putExtra("pageNumber", header);
                intent.putExtra("muscle", muscle);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muscle = "Back";
                Intent intent = new Intent(v.getContext(), ExerciseActivity.class);
                intent.putExtra("pageNumber", header);
                intent.putExtra("muscle", muscle);
                startActivity(intent);
            }
        });

        biceps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muscle = "Biceps";
                Intent intent = new Intent(v.getContext(), ExerciseActivity.class);
                intent.putExtra("pageNumber", header);
                intent.putExtra("muscle", muscle);
                startActivity(intent);
            }
        });

        legs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muscle = "Legs";
                Intent intent = new Intent(v.getContext(), ExerciseActivity.class);
                intent.putExtra("pageNumber", header);
                intent.putExtra("muscle", muscle);
                startActivity(intent);
            }
        });

        should.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muscle = "Shoulders";
                Intent intent = new Intent(v.getContext(), ExerciseActivity.class);
                intent.putExtra("pageNumber", header);
                intent.putExtra("muscle", muscle);
                startActivity(intent);
            }
        });
    }
}