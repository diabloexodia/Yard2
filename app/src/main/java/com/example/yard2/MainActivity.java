package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button qrbutton, productreceivingbutton, productdispatchbutton, statusbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrbutton=findViewById(R.id.qrbutton);
        productreceivingbutton=findViewById(R.id.productreceivingbutton);
        productdispatchbutton=findViewById(R.id.productdispatchbutton);

        qrbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent qrpage=new Intent(MainActivity.this, QRGenerator.class);
                startActivity(qrpage);
            }
        });

        productreceivingbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent productreceipt=new Intent(MainActivity.this, ProductReceive.class);
                startActivity(productreceipt);
            }
        });

        productdispatchbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent productdispatch=new Intent(MainActivity.this, ProductDispatch.class);
                startActivity(productdispatch);
            }
        });
    }
}