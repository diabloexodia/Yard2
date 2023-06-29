package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yard2.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductStatus extends AppCompatActivity {

    Button go;
    LinearLayout layout;
    TextView pid, pdesc, pquantity, currentstatus, pbin, pgrade;
    EditText productID;
    String proddesc, prodgrad;
    String binno;
    Double prodquan;
    ProgressBar progressBar;
    Handler handler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_status);
        getSupportActionBar().setTitle("View Product Status");

        go = findViewById(R.id.go);
        layout = findViewById(R.id.layout);
        pid = findViewById(R.id.pid);
        pdesc = findViewById(R.id.pdesc);
        pquantity = findViewById(R.id.pquantity);
        currentstatus = findViewById(R.id.currentstatus);
        pbin = findViewById(R.id.pbin);
        pgrade = findViewById(R.id.pgrade);
        productID = findViewById(R.id.pidEditText);
        progressBar = findViewById(R.id.loadingProgressBar2);

        handler = new Handler();

        go.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (productID.getText().toString().isEmpty())
                    Toast.makeText(ProductStatus.this, "Invalid Product ID", Toast.LENGTH_SHORT).show();
                else if(!checkSpecialCharacters(productID.getText().toString()))
                    Toast.makeText(ProductStatus.this, "Invalid Product ID", Toast.LENGTH_SHORT).show();
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.INVISIBLE);
                    fetchDataFromDatabase();
                }
            }
        });
    }

    private void fetchDataFromDatabase()
    {
        final String database_name = "rinl_yard";
        final String url = "jdbc:mysql://yard2.csze4pgxgikq.ap-southeast-1.rds.amazonaws.com/" + database_name;
        final String username = "admin", password = "admin123";
        final String table_name = "Product_Master";

        new Thread(new Runnable()
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
                    String selectQuery;
                    selectQuery = "SELECT * FROM " + table_name + " WHERE Product_Id = " + productID.getText().toString();
                    resultSet = statement.executeQuery(selectQuery);
                    if (resultSet.next())
                    {
                        proddesc = resultSet.getString("Product_Desc");
                        prodquan = resultSet.getDouble("Stock_in_Tons");
                        prodgrad = resultSet.getString("Product_grade");
                        binno = resultSet.getString("Bin_no");
//                        resultSet.close();

                        handler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                layout.setVisibility(View.VISIBLE);
                                pid.setText(productID.getText().toString());
                                pdesc.setText(proddesc);
                                pquantity.setText(Double.toString(prodquan));
                                if (binno.equals(-1))
                                    currentstatus.setText("Dispatched");
                                else
                                {
                                    currentstatus.setText("In Yard");
                                    pbin.setText(binno);
                                }
                                pgrade.setText(prodgrad);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                    else
                    {
                        handler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                layout.setVisibility(View.INVISIBLE);
                                Toast.makeText(ProductStatus.this, "Product does not exist", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    connection.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private boolean checkSpecialCharacters(String s)
    {
        for(int x=0;x<s.length();x++)
            if(!Character.isLetterOrDigit(s.charAt(x)))
                return false;
        return true;
    }
}
