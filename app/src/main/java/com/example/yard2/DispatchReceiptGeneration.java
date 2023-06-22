package com.example.yard2;

import static com.example.yard2.ReceiptGeneration.generateRandomString;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DispatchReceiptGeneration extends AppCompatActivity {
    TextView dispatchidtext, datetext, productidtext, salesorderidtext, quantitytext;
    EditText remarkset;
    Spinner transporttypespin;
    Button submitbutton;
    private String transporttypestring = " ";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_receipt_generation);
        getSupportActionBar().setTitle("Generate Dispatch Receipt");

        dispatchidtext = findViewById(R.id.dispatchIdEditText);
        datetext = findViewById(R.id.dateEdittext2);
        productidtext = findViewById(R.id.productIdEditText2);
        salesorderidtext = findViewById(R.id.salesOrderEditText);
        quantitytext = findViewById(R.id.QuantityEdittext2);
        remarkset = findViewById(R.id.RemarksEditText2);
        transporttypespin = findViewById(R.id.transportTypeSpinner2);
        submitbutton = findViewById(R.id.ReceiptGenerationSubmitButton2);

        Intent currentIntent = getIntent();

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        datetext.setText(dateFormat.format(currentDate));

        transporttypespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transporttypestring = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                transporttypestring = parent.getItemAtPosition(0).toString();
            }
        });

        productidtext.setText(currentIntent.getStringExtra("productid"));
        salesorderidtext.setText(currentIntent.getStringExtra("salesorderid"));
        quantitytext.setText(String.valueOf(currentIntent.getDoubleExtra("Quantity", -1)));
        // -----------Date-----------------------------------------------------
        currentDate = new Date();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        datetext.setText(dateFormat.format(currentDate));
        // -----------Date------------------------------------------------------

        // ------------Transport Type---------------------------------------------------------------
        transporttypespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transporttypestring = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                transporttypestring = parent.getItemAtPosition(0).toString();
            }
        });
        // ------------Transport Type---------------------------------------------------------------
        dispatchidtext.setText(generateRandomString(6));


        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent successdispatchpage = new Intent(DispatchReceiptGeneration.this, SuccessPageDispatch.class);
                successdispatchpage.putExtra("Product ID", productidtext.getText().toString());
                successdispatchpage.putExtra("Quantity", quantitytext.getText().toString());
                successdispatchpage.putExtra("Sales ID", salesorderidtext.getText().toString());
                successdispatchpage.putExtra("Date", datetext.getText().toString());
                successdispatchpage.putExtra("Remarks", remarkset.getText().toString());
                successdispatchpage.putExtra("Transport", transporttypestring);
                startActivity(successdispatchpage);
            }
        });
    }
}