package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class StatusPage extends AppCompatActivity
{
    Button productstatus, binstatus;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_page);
        Objects.requireNonNull(getSupportActionBar()).setTitle("View Status");
        productstatus=findViewById(R.id.productstatus);
        binstatus=findViewById(R.id.binstatus);

        productstatus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent productstatuspage=new Intent(StatusPage.this,ProductStatus.class);
                startActivity(productstatuspage);
            }
        });

        binstatus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent binstatuspage=new Intent(StatusPage.this, BinStatus.class);
                startActivity(binstatuspage);
            }
        });
    }
}