package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReceiptSuccessPage extends AppCompatActivity {


    TextView receiptIdTextView,productIdTextView,remarksTextView,transportTypeTextView,quantityTextView,dateTextView;
    Button confirmButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_success_page);
        getSupportActionBar().hide();
        receiptIdTextView=findViewById(R.id.receiptTextView);
        productIdTextView=findViewById(R.id.productidTextView);
        remarksTextView=findViewById(R.id.remarksTextView);
        transportTypeTextView=findViewById(R.id.transportTypeTextView);
        quantityTextView=findViewById(R.id.quantityTextView);
        dateTextView=findViewById(R.id.dateTextView);
        confirmButton=findViewById(R.id.confirmButton);

        Intent intent = getIntent();
        quantityTextView.setText("Quantity: "+intent.getStringExtra("Quantity"));
        productIdTextView.setText("Product ID: "+intent.getStringExtra("Product ID"));
        remarksTextView.setText("Remarks: "+intent.getStringExtra("Remarks"));
        transportTypeTextView.setText("Transport: "+intent.getStringExtra("Transport"));
        receiptIdTextView.setText("Receipt ID: "+intent.getStringExtra("Receipt ID"));
        dateTextView.setText("Date: "+intent.getStringExtra("Date"));

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(getApplicationContext(),MainActivity.class);
                finish();
            }
        });


    }
}