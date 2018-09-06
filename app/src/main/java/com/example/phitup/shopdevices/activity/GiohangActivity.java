package com.example.phitup.shopdevices.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phitup.shopdevices.R;
import com.example.phitup.shopdevices.adapter.GiohangAdapter;
import com.example.phitup.shopdevices.util.CheckConnection;

import java.text.DecimalFormat;

public class GiohangActivity extends AppCompatActivity {

    Toolbar toolbargiohang;
    static TextView txttongtien;
    TextView txtthongbao;
    Button btnthanhtoan, btntieptucmua;
    ListView lsvgiohang;
    GiohangAdapter giohangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);

        AnhXa();
        ActionBar();
        CheckData();
        EventUltil();
        CatchOnItemDelete();
        EventButton();

    }

    private void EventButton() {
        // event button payment
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GiohangActivity.this, MainActivity.class));
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.mangGiohang.size() > 0){
                    startActivity(new Intent(GiohangActivity.this, ThongtinkhachhangActivity.class));
                }else{
                    CheckConnection.Toast_Short(GiohangActivity.this, "Giỏ hàng của bạn hiện vẫn chưa có sản phẩm nào");
                }
            }
        });
    }

    private void CatchOnItemDelete() {
        lsvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này không");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        giohangAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.mangGiohang.size() <= 0){
                            txtthongbao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.mangGiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EventUltil();
                            if(MainActivity.mangGiohang.size() <= 0){
                                txtthongbao.setVisibility(View.VISIBLE);
                            }else{
                                txtthongbao.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });
                builder.show();
                return true;
            }
        });
    }


    public static void EventUltil() {
        long tongtien = 0;
        for(int i=0 ;i<MainActivity.mangGiohang.size();i++){
            tongtien += MainActivity.mangGiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + " Đ");
    }

    private void CheckData() {
        if(MainActivity.mangGiohang.size() <= 0){
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lsvgiohang.setVisibility(View.INVISIBLE);
        }else{
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lsvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionBar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbargiohang = findViewById(R.id.toolbargiohang);
        lsvgiohang = findViewById(R.id.listviewgiohang);
        txtthongbao = findViewById(R.id.textviewthongbao);
        txttongtien = findViewById(R.id.textviewtongtien);
        btnthanhtoan = findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua = findViewById(R.id.buttontieptucmuahang);
        giohangAdapter = new GiohangAdapter(this, MainActivity.mangGiohang);
        lsvgiohang.setAdapter(giohangAdapter);
    }
}
