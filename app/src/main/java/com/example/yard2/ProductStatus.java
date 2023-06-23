package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductStatus extends AppCompatActivity
{
    Button go;
    LinearLayout layout;
    TextView pid, pdesc, pquantity, currentstatus, pbin, pgrade;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_status);
        getSupportActionBar().setTitle("View Product Status");
        go=findViewById(R.id.go);
        layout=findViewById(R.id.layout);
        pid=findViewById(R.id.pid);
        pdesc=findViewById(R.id.pdesc);
        pquantity=findViewById(R.id.pquantity);
        currentstatus=findViewById(R.id.currentstatus);
        pbin=findViewById(R.id.pbin);
        pgrade=findViewById(R.id.pgrade);
        go.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                layout.setVisibility(View.VISIBLE);
            }
        });
    }
}