package com.example.phitup.shopdevices.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.phitup.shopdevices.R;

public class ManhinhchaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchao);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                }catch (Exception ex){

                }finally {
                    startActivity(new Intent(ManhinhchaoActivity.this, StartscreenActivity.class));
                }
            }
        });
        thread.start();
    }
}
