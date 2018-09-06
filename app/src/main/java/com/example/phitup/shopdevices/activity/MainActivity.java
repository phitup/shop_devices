package com.example.phitup.shopdevices.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.phitup.shopdevices.R;
import com.example.phitup.shopdevices.adapter.LoaispAdapater;
import com.example.phitup.shopdevices.adapter.SanphamAdapter;
import com.example.phitup.shopdevices.model.Giohang;
import com.example.phitup.shopdevices.model.Loaisp;
import com.example.phitup.shopdevices.model.Sanpham;
import com.example.phitup.shopdevices.util.CheckConnection;
import com.example.phitup.shopdevices.util.Server;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh, recyclerViewmanhinhchinhLaptop;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawable;
    ArrayList<Loaisp> mangLoaisp;
    ArrayList<Sanpham> mangSanpham, mangSanphamLaptop;
    public static ArrayList<Giohang> mangGiohang;
    LoaispAdapater loaispAdapater;
    SanphamAdapter sanphamAdapter, sanphamAdapterLaptop;

    int id, idsanphamPhone = 1, idsanphamLaptop = 2;
    String Tenloaisp = null , Hinhanhloaisp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        if(CheckConnection.haveNetworkConnection(this)){
            ActionBar();
            ActionViewFlipper();
            getDataToToolbar();
            getNewProduct();
            getNewProductLaptop();
            ClickItemListView();
        }else{
            CheckConnection.Toast_Short(this , "Bạn hãy kiểm trai lại kết nối");
            finish();
        }
    }

    private void getNewProductLaptop() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Server.duongdanSanphammoinhatlaptop,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int Id = 0;
                        String tensanpham = null;
                        Integer giasanpham = 0;
                        String hinhanhsanphan = null;
                        String motasanpham = null;
                        int idsanpham = 0 ;

                        if(response != null){
                            for (int i = 0 ; i< response.length() ;i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    Id = jsonObject.getInt("id");
                                    tensanpham = jsonObject.getString("tensanpham");
                                    giasanpham = jsonObject.getInt("giasanpham");
                                    hinhanhsanphan = jsonObject.getString("hinhanhsanpham");
                                    motasanpham = jsonObject.getString("motasanpham");
                                    idsanpham = jsonObject.getInt("idsanpham");
                                    mangSanphamLaptop.add(new Sanpham(Id, tensanpham, giasanpham, hinhanhsanphan, motasanpham, idsanpham));
                                    sanphamAdapterLaptop.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("BBB" , "loi san pham moi nhat : " + error.toString());
                    }
                });
        queue.add(request);
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

    private void ClickItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.Toast_Short(MainActivity.this, "Bạn hãy kiểm tra lại kết nối mạng");
                        }
                        drawable.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)){
                            Intent intent = new Intent(MainActivity.this, DienthoaiActivity.class);
                            intent.putExtra("idloaisp", mangLoaisp.get(i).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.Toast_Short(MainActivity.this, "Bạn hãy kiểm tra lại kết nối mạng");
                        }
                        drawable.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)){
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idloaisp", mangLoaisp.get(i).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.Toast_Short(MainActivity.this, "Bạn hãy kiểm tra lại kết nối mạng");
                        }
                        drawable.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)){
                            Intent intent = new Intent(MainActivity.this, LienheActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.Toast_Short(MainActivity.this, "Bạn hãy kiểm tra lại kết nối mạng");
                        }
                        drawable.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(MainActivity.this)){
                            Intent intent = new Intent(MainActivity.this, GooglemapActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.Toast_Short(MainActivity.this, "Bạn hãy kiểm tra lại kết nối mạng");
                        }
                        drawable.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void getNewProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanSanphammoinhat,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int Id = 0;
                        String tensanpham = null;
                        Integer giasanpham = 0;
                        String hinhanhsanphan = null;
                        String motasanpham = null;
                        int idsanpham = 0 ;

                        if(response != null){
                            for (int i = 0 ; i< response.length() ;i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    Id = jsonObject.getInt("id");
                                    tensanpham = jsonObject.getString("tensanpham");
                                    giasanpham = jsonObject.getInt("giasanpham");
                                    hinhanhsanphan = jsonObject.getString("hinhanhsanpham");
                                    motasanpham = jsonObject.getString("motasanpham");
                                    idsanpham = jsonObject.getInt("idsanpham");
                                    mangSanpham.add(new Sanpham(Id, tensanpham, giasanpham, hinhanhsanphan, motasanpham, idsanpham));
                                    sanphamAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("BBB" , "loi san pham moi nhat : " + error.toString());
                    }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDataToToolbar() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response != null){
                            for(int i=0 ; i<response.length() ; i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    Tenloaisp = jsonObject.getString("tenloaisp");
                                    Hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                                    Log.d("BBB" , id + "\n" + Tenloaisp + "\n" + Hinhanhloaisp);
                                    mangLoaisp.add(new Loaisp(id, Tenloaisp, Hinhanhloaisp));
                                    loaispAdapater.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            mangLoaisp.add(3, new Loaisp(0, "Liên Hệ" , "https://cdn4.iconfinder.com/data/icons/display-yoshicons/500/smartphone-512.png"));
                            mangLoaisp.add(4, new Loaisp(0, "Thông Tin" ,"https://cdn4.iconfinder.com/data/icons/display-yoshicons/500/smartphone-512.png"));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("BBB" , error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangQuangcao = new ArrayList<>();
//        mangQuangcao.add("http://utashop.vn/timthumb.php?src=upload/images/in-hinh-len-samsung-8160-5830.png&w=500&h=0&zc=1&a=tc");
        mangQuangcao.add("http://www.wizbytes.net/wp-content/uploads/2017/11/22673a0.jpg");
        mangQuangcao.add("https://pre00.deviantart.net/272e/th/pre/i/2013/097/e/8/iphone_6_white_advertisement_by_xerix93-d60n64g.jpg");
        mangQuangcao.add("https://static1.i4u.com/sites/default/files/imagecache/main_image_large/images/2016/11/staples-black-friday-2016-ad-1.jpg");
        mangQuangcao.add("http://mobi-lab.in/wp-content/uploads/2018/04/banner14.jpg");
        mangQuangcao.add("https://i.doanhnhansaigon.vn/2012/12/17/htc-butterfly-2-png-1355706994-500x0-1508320264.png");
        for(int i = 0 ; i< mangQuangcao.size() ; i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(this).load(mangQuangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(this , R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(this , R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawable.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewmanhinhchinh = findViewById(R.id.recyclerviewphone);
        recyclerViewmanhinhchinhLaptop = findViewById(R.id.recyclerviewlaptop);
        navigationView = findViewById(R.id.navigationview);
        listViewmanhinhchinh = findViewById(R.id.listviewmanhinhchinh);
        drawable = findViewById(R.id.drawblelayout);
        mangLoaisp = new ArrayList<>();
        mangLoaisp.add(0, new Loaisp(0, "Màn Hình Chính" , "https://cdn4.iconfinder.com/data/icons/display-yoshicons/500/smartphone-512.png"));
        loaispAdapater = new LoaispAdapater(this , mangLoaisp);
        listViewmanhinhchinh.setAdapter(loaispAdapater);

        mangSanpham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(this , mangSanpham);

        mangSanphamLaptop = new ArrayList<>();
        sanphamAdapterLaptop = new SanphamAdapter(this, mangSanphamLaptop);

        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewmanhinhchinh.setAdapter(sanphamAdapter);
        recyclerViewmanhinhchinhLaptop.setHasFixedSize(true);
        recyclerViewmanhinhchinhLaptop.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewmanhinhchinhLaptop.setAdapter(sanphamAdapterLaptop);

        if(mangGiohang != null){

        }else{
            mangGiohang = new ArrayList<>();
        }

    }

}
