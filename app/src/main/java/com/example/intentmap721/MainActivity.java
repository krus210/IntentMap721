package com.example.intentmap721;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editLocation;
    private static final int MY_PERMISSIONS_REQUEST_MAP = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        editLocation = findViewById(R.id.editLocation);
        Button buttonSearch = findViewById(R.id.buttonSearch);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_MAP);
                } else {
                    btnClick();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_MAP: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    btnClick();
                } else {
                    Toast.makeText(this,
                            getString(R.string.permission_not_granted),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void btnClick() {
        boolean isContainLetter = false;
        String location = editLocation.getText().toString();
        char[] locationChar = location.toCharArray();
        for (char element : locationChar) {
            if (Character.isLetter(element)) {
                isContainLetter = true;
                break;
            }
        }
        Uri uri;
        if (isContainLetter) {
            uri = Uri.parse("geo:?q=" + location);
        } else {
            uri = Uri.parse("geo:" + location);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
