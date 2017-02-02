package com.chrajeshkumar.nearby.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.chrajeshkumar.nearby.R;

/**
 * Created by ChRajeshKumar on 27-Jan-17.
 */

public class Splash_Screen extends AppCompatActivity  implements ActivityCompat.OnRequestPermissionsResultCallback {
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);


         t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (ActivityCompat.checkSelfPermission(Splash_Screen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(Splash_Screen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Splash_Screen.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    } else {
                        Intent intent = new Intent(Splash_Screen.this, DashBoard.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        t.start();

    }

   @Override
   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
       super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       switch (requestCode) {
           case 1:
               if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   Intent intent = new Intent(Splash_Screen.this,DashBoard.class);
                   startActivity(intent);
                   finish();
               }else {
                   //Permission not granted
                   Toast.makeText(Splash_Screen.this, "You need to grant location permission", Toast.LENGTH_LONG).show();
               }
       }
   }
}
