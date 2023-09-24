package com.example.sportemae ;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        TextView username = (TextView) findViewById(R.id.username);
//        TextView password = (TextView) findViewById(R.id.password);
//
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
//        MaterialButton signupbtn = (MaterialButton) findViewById(R.id.signupbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), SecondActivity.class);
                startActivity(intent);
//                if(username.getText().toString().equals("Lera") && password.getText().toString().equals("999")){
//                    //correct
//                    //Toast.makeText(MainActivity.this, "LOG IN SUCCESSFUL", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(v.getContext(), SecondActivity.class);
//                    startActivity(intent);
//
//                } else
//                    //incorrect
//                    Toast.makeText(MainActivity.this, "LOG IN FAILED", Toast.LENGTH_SHORT).show();
//
            }

        });
//
//        signupbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), FirstActivity.class);
//                startActivity(intent);}
//
//        });
    }
}