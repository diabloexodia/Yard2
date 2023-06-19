package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ReceiptGeneration extends AppCompatActivity {

        TextView quantityTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_generation);

        Intent intent = getIntent();
        quantityTextView=findViewById(R.id.QuantityEdittext);
        quantityTextView.setText(Integer.toString(intent.getIntExtra("Quantity",-1)));
// haha
    }


}