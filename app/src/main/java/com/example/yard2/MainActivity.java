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
        // the following change was made by @diabloexodia at 4:51 pm 17th-June-2023

        Toast.makeText(this, "New test change", Toast.LENGTH_SHORT).show();
    }
}