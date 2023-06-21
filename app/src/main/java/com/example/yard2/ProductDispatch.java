package com.example.yard2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ProductDispatch extends AppCompatActivity
{
    Button binScanner, materialScanner,generate;
    EditText sales_order_id;
    String qrText, product_id2, salesorderid;
    int bin_number2;
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

        binScanner.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startQRcodeScanning("Scan the Bin QR code");
            }
        });

        materialScanner.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startQRcodeScanning("Scan the Bin QR code");
            }
        });

        generate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                salesorderid=sales_order_id.getText().toString();

                Intent receiptgenpage=new Intent(ProductDispatch.this,DispatchReceiptGeneration.class);
                receiptgenpage.putExtra("productid",product_id2);
                receiptgenpage.putExtra("salesorderid",salesorderid);
                startActivity(receiptgenpage);
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
                    product_id2 = qrValues[0].trim();
                    Toast.makeText(this, "Material data accepted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    bin_number2=Integer.valueOf(qrText);
                    Toast.makeText(this, "Bin data accepted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}