package com.example.oscar.lanzadorgeneral;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends AppCompatActivity {

    TextView txt;
    String directorioFoto;
    ImageView imgCamara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.textView);
        imgCamara = findViewById(R.id.imgCamara);
    }

    public void lanzarCalculadora(View view)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_CALCULATOR);
        intent.putExtra("fromActivity", true);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
        else
        {
            Toast.makeText(this,"No se ha encontrado ninguna app",Toast.LENGTH_SHORT).show();
        }

    }

    public void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                txt.setText(String.valueOf(data.getDoubleExtra("resultado", 0)));
            }
        }

        if(requestCode == 2)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                Bitmap fotoBitMap = BitmapFactory.decodeFile(directorioFoto);
                imgCamara.setImageBitmap(fotoBitMap);
            }
        }
    }


    public void lanzarCamara(View view)
    {
        Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentCamara.resolveActivity(getPackageManager()) != null)
        {
            File photoFile = null;
            try
            {
                photoFile = createImageFile();
            } catch (IOException ex) { Toast.makeText(this,"Error al crear el archivo",Toast.LENGTH_SHORT).show(); }

            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.oscar.lanzadorgeneral",
                        photoFile);
                intentCamara.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                intentCamara.putExtra( MediaStore.EXTRA_SIZE_LIMIT, "720000");
                startActivityForResult(intentCamara, 2);
            }
        }
    }

    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        directorioFoto = image.getAbsolutePath();
        return image;
    }
}
