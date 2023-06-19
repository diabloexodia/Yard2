package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button productReceiveButton, qrgeneratorButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productReceiveButton=findViewById(R.id.productreceivingbutton);
        qrgeneratorButton=findViewById(R.id.qrbutton);

        productReceiveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent productReceivingPage=new Intent(getApplicationContext(),ProductReceive.class);
                startActivity(productReceivingPage);
            }
        });

        qrgeneratorButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent qrGeneratorPage=new Intent(MainActivity.this, QRGenerator.class);
                startActivity(qrGeneratorPage);
            }
        });


    }
}