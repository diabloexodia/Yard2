package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ReceiptSuccessPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_success_page);
        getSupportActionBar().hide();
    }
}