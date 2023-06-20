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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ProductReceive extends AppCompatActivity {

    static String product_id="AB457",product_grade="X1",product_description="Steel Sticks";
    static int bin_number=291,quantity=74;
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
                  //  quantity=Integer.valueOf(quantityEdittext.getText().toString());


                    final String database_name="rinl_yard";
                    final String url="jdbc:mysql://yard2.csze4pgxgikq.ap-southeast-1.rds.amazonaws.com/" +database_name;
                    final String username="admin",password="admin123";
                    final String table_name="Product_Master";

                    new Thread(() -> {
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection connection = DriverManager.getConnection(url, username, password);
                            Statement statement = connection.createStatement();
                            // add to RDS DB:
                            statement.execute("INSERT INTO " + table_name + " VALUES('" + product_id + "', '" +product_description + "', '" +quantity+" ', ' "+bin_number +"', '" +product_grade + "')");
                             connection.close();
                             } catch (Exception e) {e.printStackTrace();}
                    }).start();


                }

                Intent intent=new Intent(getApplicationContext(),ReceiptGeneration.class);
                intent.putExtra("Quantity",quantity);
                startActivity(intent);
            }
        });


    }
}