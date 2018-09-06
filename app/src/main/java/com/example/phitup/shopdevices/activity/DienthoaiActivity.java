package com.example.phitup.shopdevices.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phitup.shopdevices.R;
import com.example.phitup.shopdevices.adapter.DienthoaiAdapter;
import com.example.phitup.shopdevices.model.Sanpham;
import com.example.phitup.shopdevices.util.CheckConnection;
import com.example.phitup.shopdevices.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienthoaiActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    ArrayList<Sanpham> mangDienthoai;
    DienthoaiAdapter adapter;
    int idloaisp = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitdata = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dienthoai);

        AnhXa();
        GetIdLoaiSp();
        ActionToolbar();
        getData(page);
        LoadMoreData();

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

    private void LoadMoreData() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DienthoaiActivity.this, ChitietsanphamActivity.class);
                intent.putExtra("chitietsanpham", mangDienthoai.get(i));
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if(FirstItem + VisibleItem == TotalItem && TotalItem != 0 && isLoading == false && limitdata == false){
                    isLoading = true;
                    ThreadData thread = new ThreadData();
                    thread.start();
                }
            }
        });
    }

    public class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listView.addFooterView(footerview);
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

    private void getData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String duongdan = Server.duongdanDienthoai+String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int id = 0;
                        String tensanpham = "";
                        int giasanpham = 0;
                        String hinhanhsanpham = "";
                        String motasanpham = "";
                        int idsanpham = 0;
                        if(response != null && response.length() != 2){
                            listView.removeFooterView(footerview);
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i = 0 ; i<jsonArray.length();i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    tensanpham = jsonObject.getString("tensanpham");
                                    giasanpham = jsonObject.getInt("giasanpham");
                                    hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                                    motasanpham = jsonObject.getString("motasanpham");
                                    idsanpham = jsonObject.getInt("idsanpham");
                                    mangDienthoai.add(new Sanpham(id, tensanpham, giasanpham, hinhanhsanpham, motasanpham, idsanpham));
                                    Log.d("BBB",motasanpham);
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else{
                            limitdata = true;
                            listView.removeFooterView(footerview);
                            CheckConnection.Toast_Short(getApplicationContext(), "Đã hết dữ liệu");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("BBB", "Lỗi điện thoại : " + error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("idloaisp", String.valueOf(idloaisp));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIdLoaiSp() {
        Intent intent = getIntent();
        idloaisp = intent.getIntExtra("idloaisp", 0);
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbardienthoai);
        listView = findViewById(R.id.listviewdienthoai);
        mangDienthoai = new ArrayList<>();
        adapter = new DienthoaiAdapter(this, mangDienthoai);
        listView.setAdapter(adapter);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = layoutInflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();
    }
}
