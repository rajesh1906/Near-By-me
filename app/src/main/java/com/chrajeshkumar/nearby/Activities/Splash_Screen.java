package com.chrajeshkumar.nearby.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.chrajeshkumar.nearby.R;

/**
 * Created by ChRajeshKumar on 27-Jan-17.
 */

public class Splash_Screen extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Thread() {
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
                    Intent intent = new Intent(Splash_Screen.this,DashBoard.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
    }
}
