package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProductReceive extends AppCompatActivity {

    static String product_id,product_grade,product_description;
    static int bin_number,quantity;
    Button materialScanButton,binScanbutton,generateReceipt;
    EditText quantityEdittext;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_receive);

        materialScanButton=findViewById(R.id.materialScan);
        binScanbutton=findViewById(R.id.binScan);
        generateReceipt=findViewById(R.id.generateReceipt);
        quantityEdittext= findViewById(R.id.qtyEditText);



        materialScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //assign value for  product_id,product_grade,product_description
            }
        });


        binScanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //assign value for bin_number
            }
        });
        generateReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Toast.makeText(ProductReceive.this,quantityEdittext.getText().toString() , Toast.LENGTH_SHORT).show();

                if(quantityEdittext==null)
                    Toast.makeText(ProductReceive.this, "Input Quantity", Toast.LENGTH_SHORT).show();
                else
                {
                    quantity=Integer.valueOf(quantityEdittext.getText().toString());
                }

                Intent intent=new Intent(getApplicationContext(),ReceiptGeneration.class);
                intent.putExtra("Quantity",quantity);
                startActivity(intent);
            }
        });


    }
}