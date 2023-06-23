package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuccessPageDispatch extends AppCompatActivity
{
    TextView dispatchIdTextView,salesIdTextView,dispathDateTextView,dispatchQuantityTextView,dispatchTransportTextView,dispatchRemarksTextView;
    Button confirm;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        dispatchIdTextView = findViewById(R.id.dispatchIdTextView);
        salesIdTextView = findViewById(R.id.salesIdTextView);
        dispathDateTextView = findViewById(R.id.dispathDateTextView);
        dispatchQuantityTextView = findViewById(R.id.dispatchQuantityTextView);
        dispatchTransportTextView = findViewById(R.id.dispatchTransportTextView);
        dispatchRemarksTextView = findViewById(R.id.dispatchRemarksTextView);

        Intent dispatchSuccessIntent=getIntent();
        dispatchIdTextView.setText(dispatchSuccessIntent.getStringExtra("Dispatch ID"));
        salesIdTextView.setText(dispatchSuccessIntent.getStringExtra("Sales ID"));
        dispathDateTextView.setText(dispatchSuccessIntent.getStringExtra("Date"));
        dispatchQuantityTextView.setText(dispatchSuccessIntent.getStringExtra("Quantity"));
        dispatchTransportTextView.setText(dispatchSuccessIntent.getStringExtra("Transport"));
        dispatchRemarksTextView.setText(dispatchSuccessIntent.getStringExtra("Remarks"));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_page_dispatch);


    }
}