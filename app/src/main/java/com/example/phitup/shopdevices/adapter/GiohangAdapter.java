package com.example.phitup.shopdevices.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phitup.shopdevices.R;
import com.example.phitup.shopdevices.activity.GiohangActivity;
import com.example.phitup.shopdevices.activity.MainActivity;
import com.example.phitup.shopdevices.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {

    Context context;
    ArrayList<Giohang> mangGiohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> mangGiohang) {
        this.context = context;
        this.mangGiohang = mangGiohang;
    }

    @Override
    public int getCount() {
        return mangGiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTenGiohang, txtGiaGiohang;
        ImageView imgGiohang;
        Button btnminus, btnvalues, btnplus;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_giohang, null);
            viewHolder.txtTenGiohang = view.findViewById(R.id.textviewtengiohang);
            viewHolder.txtGiaGiohang = view.findViewById(R.id.textviewgiagiohang);
            viewHolder.imgGiohang = view.findViewById(R.id.imageviewgiohang);
            viewHolder.btnminus = view.findViewById(R.id.buttonminus);
            viewHolder.btnplus = view.findViewById(R.id.buttonplus);
            viewHolder.btnvalues = view.findViewById(R.id.buttonvalues);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Giohang giohang = mangGiohang.get(i);
        viewHolder.txtTenGiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGiohang.setText(decimalFormat.format(giohang.getGiasp()) + " Đ");
        Picasso.with(context).load(giohang.getHinhsp()).into(viewHolder.imgGiohang);
        viewHolder.btnvalues.setText(giohang.getSoluongsp() + "");
        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if(sl > 9){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }else if(sl <= 1) {
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else if(sl >= 1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) + 1;
                int slht = MainActivity.mangGiohang.get(i).getSoluongsp();
                long giaht = MainActivity.mangGiohang.get(i).getGiasp();
                MainActivity.mangGiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.mangGiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGiohang.setText(decimalFormat.format(giamoinhat) + " Đ");
                GiohangActivity.EventUltil();
                if(slmoinhat > 9){
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(10));
                }else{
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) - 1;
                int slht = MainActivity.mangGiohang.get(i).getSoluongsp();
                long giaht = MainActivity.mangGiohang.get(i).getGiasp();
                MainActivity.mangGiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.mangGiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGiohang.setText(decimalFormat.format(giamoinhat) + " Đ");
                GiohangActivity.EventUltil();
                if(slmoinhat <= 1){
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(1));
                }else{
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return view;
    }
}
