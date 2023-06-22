package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button productReceiveButton, qrgeneratorButton, productDispatchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Home");

        productReceiveButton=findViewById(R.id.productreceivingbutton);
        qrgeneratorButton=findViewById(R.id.qrbutton);
        productDispatchButton=findViewById(R.id.productdispatchbutton);

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
                Intent qrGeneratorPage=new Intent(MainActivity.this, GenerateQR.class);
                startActivity(qrGeneratorPage);
            }
        });

        productDispatchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent productdispatchpage=new Intent(MainActivity.this, ProductDispatch.class);
                startActivity(productdispatchpage);
            }
        });

    }
}