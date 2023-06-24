package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SuccessPageDispatch extends AppCompatActivity
{
    TextView dispatchIdTextView2,salesIdTextView,dispathDateTextView,dispatchQuantityTextView,dispatchTransportTextView,dispatchRemarksTextView;
    Button confirm;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {           super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_page_dispatch);


        dispatchIdTextView2 = findViewById(R.id.dispatchsuccessTextView);
        salesIdTextView = findViewById(R.id.dispatchSuccessSalesIDText);
        dispathDateTextView = findViewById(R.id.dispatchsuccessDateText);
        dispatchQuantityTextView = findViewById(R.id.dispatchsuccessQuantity);
        dispatchTransportTextView = findViewById(R.id.dispatchSuccessTransport);
        dispatchRemarksTextView = findViewById(R.id.dispatchSuccessRemarks);
        confirm=findViewById(R.id.buttonDispatchConfirm);

        //Toast.makeText(this, getIntent().getStringExtra("Dispatch ID"), Toast.LENGTH_SHORT).show();

        Intent dispatchSuccessIntent=getIntent();
        dispatchIdTextView2.setText(dispatchSuccessIntent.getStringExtra("Dispatch ID"));

        salesIdTextView.setText(dispatchSuccessIntent.getStringExtra("Sales ID"));
        dispathDateTextView.setText(dispatchSuccessIntent.getStringExtra("Date"));
        dispatchQuantityTextView.setText(dispatchSuccessIntent.getStringExtra("Quantity"));
        dispatchTransportTextView.setText(dispatchSuccessIntent.getStringExtra("Transport"));
        dispatchRemarksTextView.setText(dispatchSuccessIntent.getStringExtra("Remarks"));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent successMain=new Intent(getApplicationContext(), MainActivity.class);

              //  clears the activity stack
                successMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(successMain);
                finish();
            }
        });


    }
}