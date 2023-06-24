package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BinStatus extends AppCompatActivity
{
    EditText binno;
    Button go2;
    TextView binnot, bindesc, binloc, binstatus;
    LinearLayout layout;
    ProgressBar bar;
    String bindescval, binlocval;
    int binnoval;
    Executor executor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_status);
        binno = findViewById(R.id.binnoEditText);
        go2 = findViewById(R.id.go2);
        binnot = findViewById(R.id.binno);
        bindesc = findViewById(R.id.bindesc);
        binloc = findViewById(R.id.binloc);
        layout = findViewById(R.id.l2);
        bar = findViewById(R.id.loadingProgressBarx);
        binstatus = findViewById(R.id.binstatus);

        executor = Executors.newSingleThreadExecutor();

        go2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                binnoval = Integer.parseInt(binno.getText().toString());
                bar.setVisibility(View.VISIBLE);
                fetchDataFromDatabase();
            }
        });
    }

    private void fetchDataFromDatabase()
    {
        final String database_name = "rinl_yard";
        final String url = "jdbc:mysql://yard2.csze4pgxgikq.ap-southeast-1.rds.amazonaws.com/" + database_name;
        final String username = "admin", password = "admin123";
        final String table_name = "Bin_Master";

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
                    String selectQuery;
                    selectQuery = "SELECT * FROM " + table_name + " WHERE Bin_No = " + binnoval;
                    resultSet = statement.executeQuery(selectQuery);
                    if (resultSet.next())
                    {
                        bindescval = resultSet.getString("Bin_Desc");
                        binlocval = resultSet.getString("Bin_Location");
                    }
                    connection.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        layout.setVisibility(View.VISIBLE);
                        binnot.setText(Integer.toString(binnoval));
                        bindesc.setText(bindescval);
                        binloc.setText(binlocval);
                        checkProductInBin();
                    }
                });
            }
        });
    }

    private void checkProductInBin()
    {
        final String database_name = "rinl_yard";
        final String url = "jdbc:mysql://yard2.csze4pgxgikq.ap-southeast-1.rds.amazonaws.com/" + database_name;
        final String username = "admin", password = "admin123";
        final String table_name = "Product_Master";

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
                    String selectQuery;
                    selectQuery = "SELECT * FROM " + table_name + " WHERE Bin_No = " + binnoval;
                    resultSet = statement.executeQuery(selectQuery);
                    boolean hasProduct = resultSet.next();
                    connection.close();

                    final boolean finalHasProduct = hasProduct;
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            bar.setVisibility(View.GONE);
                            if (finalHasProduct)
                            {
                                binstatus.setText("Bin contains a product");
                            }
                            else
                            {
                                binstatus.setText("Bin is empty");
                            }
                        }
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
