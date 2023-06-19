package com.example.yard2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BinQRGenerator extends AppCompatActivity
{
    ImageView qrcode;
    EditText inputText;
    Button generatebutton, savepdfbutton;
    Bitmap qrCodeBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binqrgenerator);
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
        savepdfbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                File pdfFile=convertBitmapToPdf(qrCodeBitmap,inputText.getText().toString());
                printPdf(pdfFile);
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
    private File convertBitmapToPdf(Bitmap qrCodeBitmap, String filename)
    {
        try
        {
            String filePath = getFilesDir().getPath() + File.separator + filename + ".pdf";
            File pdfFile = new File(filePath);
//            File pdfFile = new File(getFilesDir(), filename);
            OutputStream outputStream = new FileOutputStream(pdfFile);

            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDocument);

            com.itextpdf.layout.element.Image qrCodeImage = new com.itextpdf.layout.element.Image(ImageDataFactory.create(bitmapToByteArray(qrCodeBitmap)));
            document.add(qrCodeImage);

            document.close();
            outputStream.close();

            return pdfFile;
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error generating the QR pdf", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    private byte[] bitmapToByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
    private void printPdf(File pdfFile)
    {
        if (pdfFile != null)
        {
            Uri pdfUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", pdfFile);

            Intent printIntent = new Intent(Intent.ACTION_SEND);
            printIntent.setType("application/pdf");
            printIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
            printIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try
            {
                startActivity(Intent.createChooser(printIntent, "Print PDF"));
            }
            catch (ActivityNotFoundException e)
            {
                Toast.makeText(this, "No printing app available.", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "PDF file not generated", Toast.LENGTH_SHORT).show();
        }
    }
}