package com.example.appcameraexterna;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> galleryActivityResultLauncher;
    ActivityResultLauncher<Intent> cameraActivityResultLauncher;
    public Uri photoURI;
    String currentPhotoPath;
    public static int RC_PHOTO_PICKER = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button boton_cargar = findViewById(R.id.boton_cargar);
        Button boton_foto = findViewById(R.id.boton_foto);
        galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            ImageView imageView = findViewById(R.id.img);
                            imageView.setImageURI(uri);
                        }
                    }
                });

        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Bundle extras = result.getData().getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            ImageView imageView = findViewById(R.id.img);
                            imageView.setImageBitmap(imageBitmap);
                        }
                    }
                });
        boton_cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSomeActivityForResult(view);
            }
        });

        boton_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSomeActivityForResult2(view);
            }
        });
    }

    public void openSomeActivityForResult(View view) {

        //Create Intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        //Launch activity to get result
        galleryActivityResultLauncher.launch(intent);
    }

    public void openSomeActivityForResult2(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        cameraActivityResultLauncher.launch(takePictureIntent);

    }
}