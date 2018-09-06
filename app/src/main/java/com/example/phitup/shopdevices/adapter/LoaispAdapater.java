package com.example.phitup.shopdevices.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phitup.shopdevices.R;
import com.example.phitup.shopdevices.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispAdapater extends BaseAdapter {

    Context context;
    ArrayList<Loaisp> mangloaisp;

    public LoaispAdapater(Context context, ArrayList<Loaisp> mangloaisp) {
        this.context = context;
        this.mangloaisp = mangloaisp;
    }

    @Override
    public int getCount() {
        return mangloaisp.size();
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
        ImageView imgLoaisp;
        TextView txtTenloaisp;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_loaisp_layout , null);

            viewHolder = new ViewHolder();
            viewHolder.imgLoaisp = view.findViewById(R.id.imageviewLoaisp);
            viewHolder.txtTenloaisp = view.findViewById(R.id.textviewTenLoaisp);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Loaisp loaisp = mangloaisp.get(i);
        viewHolder.txtTenloaisp.setText(loaisp.getTenloaisp());
        Picasso.with(context).load(loaisp.getHinhanhloaisp()).into(viewHolder.imgLoaisp);

        return view;
    }
}
