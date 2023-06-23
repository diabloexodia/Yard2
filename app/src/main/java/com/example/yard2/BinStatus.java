package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BinStatus extends AppCompatActivity
{
    EditText binno;
    Button go2;
    TextView binnot, bindesc, binloc;
    LinearLayout layout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_status);
        binno=findViewById(R.id.binnoEditText);
        go2=findViewById(R.id.go2);
        binnot=findViewById(R.id.binno);
        bindesc=findViewById(R.id.bindesc);
        binloc=findViewById(R.id.binloc);
        layout=findViewById(R.id.l2);
        go2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                layout.setVisibility(View.VISIBLE);
            }
        });
    }
}