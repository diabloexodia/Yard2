package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptGeneration extends AppCompatActivity {

         TextView quantityTextView,productIdTextView,dateTextView,receiptTextView;
         EditText remarksEditText;
        Button sumbitButton;
       private String transportTextView;
        Spinner transportTypeSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_generation);

        Intent intent = getIntent();
        quantityTextView=findViewById(R.id.QuantityEdittext);
        remarksEditText=findViewById(R.id.RemarksEditText);
        receiptTextView=findViewById(R.id.receiptIdEditText);
        dateTextView=findViewById(R.id.dateEdittext);
        transportTypeSpinner = findViewById(R.id.transportTypeSpinner);
        productIdTextView=findViewById(R.id.productIdEditText);

        quantityTextView.setText(Integer.toString(intent.getIntExtra("Quantity",-1)));
        productIdTextView.setText(intent.getStringExtra("Product ID"));

        // -----------Date-----------------------------------------------------
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateTextView.setText(dateFormat.format(currentDate));
        // -----------Date------------------------------------------------------



        // ------------Transport Type---------------------------------------------------------------
        transportTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transportTextView = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                transportTextView = parent.getItemAtPosition(0).toString();
            }
        });
        // ------------Transport Type---------------------------------------------------------------

        //-------------------Receipt ID------------------
        receiptTextView.setText("76AHE");
        //------------------Receipt ID-------------------

        sumbitButton=findViewById(R.id.ReceiptGenerationSubmitButton);
        sumbitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(quantityTextView.getText().toString().equals("-1")|| remarksEditText.getText().toString().equals(" "))
                    Toast.makeText(ReceiptGeneration.this, "Check Quantity or Remarks", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent2 = new Intent(ReceiptGeneration.this, ReceiptSuccessPage.class);
                    intent2.putExtra("Product ID",productIdTextView.getText().toString());
                    intent2.putExtra("Quantity",quantityTextView.getText().toString());
                    intent2.putExtra("Receipt ID",receiptTextView.getText().toString());
                    intent2.putExtra("Date",dateTextView.getText().toString());
                    intent2.putExtra("Remarks",remarksEditText.getText().toString());
                    intent2.putExtra("Transport",transportTextView);
                    startActivity(intent2);
                }
            }
        });

    }


}