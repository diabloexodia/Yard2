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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductReceive extends AppCompatActivity
{
    String qrText;
    String product_id, product_grade, product_description;
    String bin_number;
    int quantity;
    Button materialScanButton, binScanbutton, generateReceipt;
    EditText quantityEdittext;
    TextInputLayout quantitytil;
    final String database_name = "rinl_yard";
    final String url = "jdbc:mysql://yard2.csze4pgxgikq.ap-southeast-1.rds.amazonaws.com/" + database_name;
    final String username = "admin", password = "admin123";
    final String table_name = "Product_Master";
    Executor executor;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_receive);
        getSupportActionBar().setTitle("Product Receipt");

        materialScanButton = findViewById(R.id.materialScan);
        binScanbutton = findViewById(R.id.binScan);
        generateReceipt = findViewById(R.id.generateReceipt);
        quantityEdittext = findViewById(R.id.qtyEditText);
        quantitytil = findViewById(R.id.quantityTextInputLayout);

        product_id = "";
        product_grade = "";
        product_description = "";
        qrText = "";
        bin_number = "";

        executor = Executors.newSingleThreadExecutor();

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
            public void onClick(View view) {
                startQRcodeScanning("Scan the Bin QR code");
            }
        });

        generateReceipt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (quantityEdittext.getText().toString().isEmpty())
                {
                    quantitytil.setError("Quantity is needed");
                    Toast.makeText(ProductReceive.this, "Input Quantity", Toast.LENGTH_SHORT).show();
                }
                else if (product_id.equals(""))
                {
                    Toast.makeText(ProductReceive.this, "Please scan the Material QR Code", Toast.LENGTH_SHORT).show();
                }
                else if (bin_number.isEmpty())
                {
                    Toast.makeText(ProductReceive.this, "Please scan Bin QR Code", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    quantity = Integer.parseInt(quantityEdittext.getText().toString());
                    executor.execute(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Class.forName("com.mysql.jdbc.Driver");
                                Connection connection = DriverManager.getConnection(url, username, password);
                                Statement statement = connection.createStatement();

                                statement.execute("INSERT INTO " + table_name + " VALUES('" + product_id + "', '" + product_description + "', '" + quantity + "', '" + bin_number + "', '" + product_grade + "')");
                                //connection.close();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                    Intent intent = new Intent(getApplicationContext(), ReceiptGeneration.class);
                    intent.putExtra("Quantity", quantity);
                    intent.putExtra("Product ID", product_id);
                    startActivity(intent);
                }
            }
        });
    }

    private void startQRcodeScanning(String displayText)
    {
        IntentIntegrator integrator = new IntentIntegrator(this);
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

                if (qrValues.length == 2 || qrValues.length == 3)
                {
                    product_id = qrValues[0].trim();
                    product_grade = qrValues[1].trim();
                    if(qrValues.length==3)
                        product_description = qrValues[2].trim();
                    executor.execute(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Class.forName("com.mysql.jdbc.Driver");
                                Connection connection = DriverManager.getConnection(url, username, password);
                                Statement statement = connection.createStatement();

                                ResultSet resultSet;
                                String selectQuery = "SELECT * FROM " + table_name + " WHERE Product_Id = '" + product_id + "'";
                                resultSet = statement.executeQuery(selectQuery);
                                if (resultSet.next())
                                {
                                    product_id = "";
                                    product_grade="";
                                    product_description="";
                                    runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            Toast.makeText(ProductReceive.this, "Invalid Material data. Material data already exists.", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                                else
                                {
                                    runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            Toast.makeText(ProductReceive.this, "Material data accepted", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                //connection.close();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else if(qrText.length()<=6)
                {
                    executor.execute(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Class.forName("com.mysql.jdbc.Driver");
                                Connection connection = DriverManager.getConnection(url, username, password);
                                Statement statement = connection.createStatement();

                                ResultSet resultSet;
                                String selectQuery = "SELECT * FROM " + table_name + " WHERE Bin_No = '" + qrText + "'";
                                resultSet = statement.executeQuery(selectQuery);
                                if (resultSet.next())
                                {
                                    bin_number = "";
                                    runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            Toast.makeText(ProductReceive.this, "Incorrect Bin Scanned. Bin already in use.", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                                else
                                {
                                    bin_number = qrText;
                                    runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            Toast.makeText(ProductReceive.this, "Bin data accepted", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                //connection.close();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(this, "Invalid QR detected", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}