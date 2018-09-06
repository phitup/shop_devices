package com.example.phitup.shopdevices.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.phitup.shopdevices.R;

public class StartscreenActivity extends AppCompatActivity {

    Button btnDangky, btnDangnhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);

        AnhXa();

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartscreenActivity.this, DangkyActivity.class));
            }
        });

        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartscreenActivity.this, DangnhapActivity.class));
            }
        });
    }

    private void AnhXa() {
        btnDangky = findViewById(R.id.buttondangky);
        btnDangnhap = findViewById(R.id.buttondangnhap);
    }
}

