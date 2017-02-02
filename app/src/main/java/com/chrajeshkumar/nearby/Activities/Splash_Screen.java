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
    Handler handler;
    private int STORAGE_PERMISSION_CODE = 23;
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
        /*new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                } catch (NullPointerException ex) {
                    Log.e("Handler Exception :", ex.toString());

                }
            }

            ;
        }.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                try {
                    if (ActivityCompat.checkSelfPermission(Splash_Screen.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            ) {
                        // Camera permission has not been granted.

                        requestLocationPermission();


                    } else {

                        Intent intent = new Intent(Splash_Screen.this,DashBoard.class);
                        startActivity(intent);
                        finish();

                    }



                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };*/
    }

    public void requestLocationPermission(){
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.LOCATION_HARDWARE},
//                1);
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            // BEGIN_INCLUDE(permission_result)
            Log.e("<><><", "Received response for location permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Camera permission has been granted, preview can be displayed
                Intent intent = new Intent(Splash_Screen.this,DashBoard.class);
                startActivity(intent);
                finish();

            } else {
                //Permission not granted
                Toast.makeText(Splash_Screen.this, "You need to grant location permission", Toast.LENGTH_LONG).show();
            }

        }
    }*/
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
