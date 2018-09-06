package com.example.phitup.shopdevices.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.phitup.shopdevices.R;
import com.example.phitup.shopdevices.util.CheckConnection;
import com.example.phitup.shopdevices.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongtinkhachhangActivity extends AppCompatActivity {

    EditText editTextTen, editTextSodienthoai, editTextDiachi, editTextEmail;
    Button btnXacnhan, btnTrove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);

        AnhXa();
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                    EventButton();
                }else{
                    CheckConnection.Toast_Short(ThongtinkhachhangActivity.this, "Hạy kiểm tra lại kết nối");
                }
            }
        });

    }

    private void EventButton() {
        final String getTen = editTextTen.getText().toString().trim();
        final String getsdt = editTextSodienthoai.getText().toString().trim();
        final String getDiachi = editTextDiachi.getText().toString().trim();
        final String getEmail = editTextEmail.getText().toString().trim();
        if(!getTen.isEmpty() && !getsdt.isEmpty() && !getDiachi.isEmpty() && !getEmail.isEmpty()) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanThongtinkhachhang,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            if(Integer.parseInt(madonhang) >0){
                                RequestQueue queue = Volley.newRequestQueue(ThongtinkhachhangActivity.this);
                                StringRequest request = new StringRequest(Request.Method.POST, Server.duongdanChitietdonhang,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                if(response.equals("1")){
                                                    MainActivity.mangGiohang.clear();
                                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                    CheckConnection.Toast_Short(getApplicationContext(), "Bạn đã đặt hàng thành công");
                                                }else{
                                                    CheckConnection.Toast_Short(getApplicationContext(), "Đặt hàng thất bại");
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i = 0 ; i<MainActivity.mangGiohang.size() ;i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", madonhang);
                                                jsonObject.put("masanpham", MainActivity.mangGiohang.get(i).getIdsp());
                                                jsonObject.put("tensanpham", MainActivity.mangGiohang.get(i).getTensp());
                                                jsonObject.put("giasanpham", MainActivity.mangGiohang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham", MainActivity.mangGiohang.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }else{
                                Toast.makeText(ThongtinkhachhangActivity.this, madonhang, Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("BBB", "Lỗi ở phần Thông Tin Khách hàng : " + error.toString());
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("tenkhachhang", getTen);
                    map.put("sodienthoai", getsdt);
                    map.put("diachikhachhang", getDiachi);
                    map.put("email", getEmail);
                    return map;
                }
            };
            requestQueue.add(stringRequest);
        }else{
            CheckConnection.Toast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại dữ liệu");
        }

    }

    private void AnhXa() {
        editTextTen = findViewById(R.id.edittexttenkhachhang);
        editTextSodienthoai = findViewById(R.id.edittextsodienthoai);
        editTextDiachi = findViewById(R.id.edittextdiachi);
        editTextEmail = findViewById(R.id.edittextemail);
        btnXacnhan = findViewById(R.id.buttonxacnhan);
        btnTrove = findViewById(R.id.buttontrove);
    }
}
