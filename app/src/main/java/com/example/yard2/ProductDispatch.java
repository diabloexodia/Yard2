package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ProductDispatch extends AppCompatActivity
{
    Button binScanner, materialScanner,generate;
    EditText sales_order_id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dispatch);
        binScanner=findViewById(R.id.binScan2);
        materialScanner=findViewById(R.id.materialScan2);
        sales_order_id=findViewById(R.id.soiEditText);
        generate=findViewById(R.id.generateReceipt2);
    }
}