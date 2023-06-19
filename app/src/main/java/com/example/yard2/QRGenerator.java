package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRGenerator extends AppCompatActivity
{
    ImageView qrcode;
    EditText inputText;
    Button generatebutton, savepdfbutton;
    Bitmap qrCodeBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);
        qrcode=findViewById(R.id.qrgenerator);
        inputText=findViewById(R.id.inputText);
        generatebutton=findViewById(R.id.generatebutton);
        savepdfbutton=findViewById(R.id.savepdfbutton2);
        generatebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String inputString=inputText.getText().toString();
                qrCodeBitmap=generateQRCode(inputString);
                qrcode.setImageBitmap(qrCodeBitmap);
            }
        });
    }
    private Bitmap generateQRCode(String inputString)
    {
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        try
        {
            BitMatrix bitMatrix=qrCodeWriter.encode(inputString, BarcodeFormat.QR_CODE, 400,400);
            int width= bitMatrix.getWidth();
            int height= bitMatrix.getHeight();
            Bitmap qrCodeBitmap=Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for(int x=0;x<width;x++)
                for(int y=0;y<height;y++)
                    qrCodeBitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(android.R.color.black) : getResources().getColor(android.R.color.white));
            return qrCodeBitmap;
        }
        catch(WriterException e)
        {
            Toast.makeText(this, "Error generating QR code", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}