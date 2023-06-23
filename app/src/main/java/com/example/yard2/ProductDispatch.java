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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ProductDispatch extends AppCompatActivity {
    Button binScanner, materialScanner, generate;
    EditText sales_order_id,salesOrderRemarks;
    String qrText, product_id2, salesorderid, productID = "";
    double quantity = -1.0;
    int bin_number2 = -1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dispatch);
        binScanner = findViewById(R.id.binScan2);
        materialScanner = findViewById(R.id.materialScan2);
        sales_order_id = findViewById(R.id.soiEditText);
        generate = findViewById(R.id.generateReceipt2);
        salesOrderRemarks=findViewById(R.id.salesOrderRemarks);

        // -----------Date-----------------------------------------------------
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // -----------Date------------------------------------------------------
        binScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQRcodeScanning("Scan the Bin QR code");
            }
        });

        materialScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQRcodeScanning("Scan the Bin QR code");
            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salesorderid = sales_order_id.getText().toString();

                final String database_name = "rinl_yard";
                final String url = "jdbc:mysql://yard2.csze4pgxgikq.ap-southeast-1.rds.amazonaws.com/" + database_name;
                final String username = "admin", password = "admin123";
                final String table_name = "Product_Master";

                if (productID.isEmpty() && bin_number2 == -1) {
                    Toast.makeText(ProductDispatch.this, "Scan the material or bin first", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(() -> {
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection connection = DriverManager.getConnection(url, username, password);
                            Statement statement = connection.createStatement();
                            ResultSet resultSet;
                            String selectQuery;
                            if (bin_number2 != -1) {
                                selectQuery = "SELECT Product_Id,Stock_In_Tons FROM " + table_name + " WHERE Bin_No = " + bin_number2;
                                resultSet = statement.executeQuery(selectQuery);
                                if (resultSet.next()) {
                                    productID = resultSet.getString("Product_Id");
                                    quantity = resultSet.getDouble("Stock_In_Tons");
                                }
                            } else {
                                selectQuery = "SELECT Stock_In_Tons FROM " + table_name + " WHERE Product_Id = " + productID;
                                resultSet = statement.executeQuery(selectQuery);
                                if (resultSet.next()) {
                                    quantity = resultSet.getDouble("Stock_In_Tons");
                                }
                            }
                            //connection.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }

                // Sales order ID will already be there(input manually) in the sales _Order table , add remarks and date into the sales_order table
                //add that code below then take to next page



                if (quantity != -1.0 && !productID.isEmpty()) {


                    final String table_name3 = "Sales_Order";

                    new Thread(() -> {


                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection connection = DriverManager.getConnection(url, username, password);
                            Statement statement = connection.createStatement();
                            String updateQuery = "UPDATE " + table_name3 + " SET Sales_Order_Date = ?, Product_Id = ?, Sales_Order_Qty_in_Tons = ?, Remarks = ? WHERE Sales_Order_Id = "+salesorderid;
                            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                            preparedStatement.setString(1,dateFormat.format(currentDate) );
                            preparedStatement.setString(2, productID);
                            preparedStatement.setString(3, String.valueOf(quantity));
                            preparedStatement.setString(4, salesOrderRemarks.getText().toString());
                            preparedStatement.execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();

                    Intent receiptgenpage = new Intent(ProductDispatch.this, DispatchReceiptGeneration.class);
                    receiptgenpage.putExtra("productid", productID);
                    receiptgenpage.putExtra("salesorderid", salesorderid);
                    receiptgenpage.putExtra("Quantity", quantity);
                    startActivity(receiptgenpage);
                } else {
                    String [] waitingMessage={"Please wait" , "Generating", "Still Generating"};
                    Toast.makeText(ProductDispatch.this, waitingMessage[new Random().nextInt(waitingMessage.length)], Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void startQRcodeScanning(String displayText) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt(displayText);
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.initiateScan();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scanning cancelled", Toast.LENGTH_SHORT).show();
            } else {
                qrText = result.getContents();
                String[] qrValues = qrText.split(",");

                if (qrValues.length == 3) {
                    product_id2 = qrValues[0].trim();
                    Toast.makeText(this, "Material data accepted", Toast.LENGTH_SHORT).show();
                    productID = product_id2;

                } else {
                    bin_number2 = Integer.valueOf(qrText);
                    Toast.makeText(this, "Bin data accepted", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
}
