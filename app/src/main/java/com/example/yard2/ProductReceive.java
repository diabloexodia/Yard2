package com.example.yard2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ProductReceive extends AppCompatActivity
{
    String qrText;
    String product_id,product_grade,product_description;
    static int bin_number,quantity;
    Button materialScanButton,binScanbutton,generateReceipt;
    EditText quantityEdittext;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState)
    { //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_receive);

        materialScanButton=findViewById(R.id.materialScan);
        binScanbutton=findViewById(R.id.binScan);
        generateReceipt=findViewById(R.id.generateReceipt);
        quantityEdittext= findViewById(R.id.qtyEditText);

        product_id="";
        product_grade="";
        product_description="";
        qrText="";

        materialScanButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startQRcodeScanning("Scan the Material QR code");
            }
        });

        binScanbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startQRcodeScanning("Scan the Bin QR code");
            }
        });
        generateReceipt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
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
    private void startQRcodeScanning(String displayText)
    {
        IntentIntegrator integrator=new IntentIntegrator(this);
        integrator.setPrompt(displayText);
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.initiateScan();
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null)
        {
            if (result.getContents() == null)
            {
                Toast.makeText(this, "Scanning cancelled", Toast.LENGTH_SHORT).show();
            }
            else
            {
                qrText = result.getContents();
                String[] qrValues = qrText.split(",");

                if (qrValues.length == 3)
                {
                    product_id = qrValues[0].trim();
                    product_grade = qrValues[1].trim();
                    product_description = qrValues[2].trim();
                }
                else
                {
                    bin_number=Integer.valueOf(qrText);
                }
            }
        }
    }
}