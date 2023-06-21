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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptGeneration extends AppCompatActivity {

    TextView quantityTextView, productIdTextView, dateTextView, receiptTextView;
    EditText remarksEditText;
    Button sumbitButton;
    Spinner transportTypeSpinner;
    private String transportTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_generation);
        getSupportActionBar().setTitle("Generate Receipt");

        Intent intent = getIntent();
        quantityTextView = findViewById(R.id.QuantityEdittext);
        remarksEditText = findViewById(R.id.RemarksEditText);
        receiptTextView = findViewById(R.id.receiptIdEditText);
        dateTextView = findViewById(R.id.dateEdittext);
        transportTypeSpinner = findViewById(R.id.transportTypeSpinner);
        productIdTextView = findViewById(R.id.productIdEditText);

        quantityTextView.setText(Integer.toString(intent.getIntExtra("Quantity", -1)));
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

        sumbitButton = findViewById(R.id.ReceiptGenerationSubmitButton);
        sumbitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quantityTextView.getText().toString().equals("-1") || remarksEditText.getText().toString().equals(" "))
                    Toast.makeText(ReceiptGeneration.this, "Check Quantity or Remarks", Toast.LENGTH_SHORT).show();
                else {


                    final String database_name = "rinl_yard";
                    final String url = "jdbc:mysql://yard2.csze4pgxgikq.ap-southeast-1.rds.amazonaws.com/" + database_name;
                    final String username = "admin", password = "admin123";
                    final String table_name = "Yard_Receipts";

                    new Thread(() -> {

//
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection connection = DriverManager.getConnection(url, username, password);
                            Statement statement = connection.createStatement();
                            // Add to RDS DB with proper value for the foreign key:
                            String insertQuery = "INSERT INTO " + table_name + " VALUES(?, ?, ?, ?, ?, ?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                            preparedStatement.setString(1, receiptTextView.getText().toString());
                            preparedStatement.setString(2, transportTextView);
                            preparedStatement.setString(3, productIdTextView.getText().toString());
                            preparedStatement.setString(4, dateTextView.getText().toString());
                            preparedStatement.setString(5, quantityTextView.getText().toString());
                            preparedStatement.setString(6, remarksEditText.getText().toString());
                            preparedStatement.execute();
                            connection.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();


                    Intent intent2 = new Intent(ReceiptGeneration.this, ReceiptSuccessPage.class);
                    intent2.putExtra("Product ID", productIdTextView.getText().toString());
                    intent2.putExtra("Quantity", quantityTextView.getText().toString());
                    intent2.putExtra("Receipt ID", receiptTextView.getText().toString());
                    intent2.putExtra("Date", dateTextView.getText().toString());
                    intent2.putExtra("Remarks", remarksEditText.getText().toString());
                    intent2.putExtra("Transport", transportTextView);
                    startActivity(intent2);
                }
            }
        });

    }


}