package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(1);
        System.out.println();

        // Dhyan destroyed the change
//        Toast.makeText(this, "New test change", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "PRANITH MADE THIS CHANGE BRO", Toast.LENGTH_SHORT).show();

    }
}