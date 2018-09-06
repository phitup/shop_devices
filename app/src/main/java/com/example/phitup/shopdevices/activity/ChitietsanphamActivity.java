package com.example.phitup.shopdevices.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.phitup.shopdevices.R;
import com.example.phitup.shopdevices.model.Giohang;
import com.example.phitup.shopdevices.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChitietsanphamActivity extends AppCompatActivity {

    Toolbar toolbarChitiet;
    TextView txtTenChitiet, txtGiaChitiet, txtMotaChitiet;
    ImageView imgHinhChitiet;
    Spinner spinner;
    Button btnThemgiohang;

    int idchitiet = 0;
    String tenchitiet = "";
    int giachitiet = 0;
    String hinhanhchitiet = "";
    String motachitiet = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);

        AnhXa();
        ActionToolBar();
        getDataFromPrevious();
        EventSpinner();
        EventButton();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cart:
                startActivity(new Intent(getApplicationContext(), GiohangActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btnThemgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.mangGiohang.size() > 0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for(int i = 0 ; i < MainActivity.mangGiohang.size() ;i++){
                        if(MainActivity.mangGiohang.get(i).getIdsp() == idchitiet){
                            MainActivity.mangGiohang.get(i).setSoluongsp(MainActivity.mangGiohang.get(i).getSoluongsp() + sl);
                            if(MainActivity.mangGiohang.get(i).getSoluongsp() >= 10){
                                MainActivity.mangGiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.mangGiohang.get(i).setGiasp(giachitiet * MainActivity.mangGiohang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if(exists == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi = soluong * giachitiet;
                        MainActivity.mangGiohang.add(new Giohang(idchitiet,tenchitiet,giamoi,hinhanhchitiet,soluong));
                    }
                }else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi = soluong * giachitiet;
                    MainActivity.mangGiohang.add(new Giohang(idchitiet,tenchitiet,giamoi,hinhanhchitiet,soluong));
                }
                startActivity(new Intent(ChitietsanphamActivity.this, GiohangActivity.class));
            }
        });
    }

    private void EventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void getDataFromPrevious() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("chitietsanpham");

        idchitiet = sanpham.getId();
        tenchitiet = sanpham.getTensanpham();
        giachitiet = sanpham.getGiasanpham();
        hinhanhchitiet = sanpham.getHinhanhsanpham();
        motachitiet = sanpham.getMotasanpham();

        txtTenChitiet.setText(tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,####,###");
        txtGiaChitiet.setText("Giá : " + decimalFormat.format(giachitiet) + " Đ");
        txtMotaChitiet.setText(motachitiet);
        Picasso.with(this).load(hinhanhchitiet).into(imgHinhChitiet);

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarChitiet = findViewById(R.id.toolbarchitietsanpham);
        txtTenChitiet = findViewById(R.id.textviewTenchitietsanpham);
        txtGiaChitiet = findViewById(R.id.textviewGiachitietsanpham);
        txtMotaChitiet = findViewById(R.id.textviewMotachitietsanpham);
        imgHinhChitiet = findViewById(R.id.imageviewchitietsanpham);
        spinner = findViewById(R.id.spinner);
        btnThemgiohang = findViewById(R.id.buttonThemgioihangchitietsanpham);
    }
}
