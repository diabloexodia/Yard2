package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GenerateQR extends AppCompatActivity
{
    Button binqrgeneratorbutton, materialqrgeneratorbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);
        binqrgeneratorbutton=findViewById(R.id.bingenerator);
        materialqrgeneratorbutton=findViewById(R.id.materialgenerator);
        binqrgeneratorbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent bingeneratorpage=new Intent(GenerateQR.this, BinQRGenerator.class);
                startActivity(bingeneratorpage);
            }
        });
        materialqrgeneratorbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Intent materialgeneratorpage=new Intent(GenerateQR.this,MaterialQRGenerator.class);
            }
        });
    }
}