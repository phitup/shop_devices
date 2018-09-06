package com.example.phitup.shopdevices.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phitup.shopdevices.R;
import com.example.phitup.shopdevices.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> mangSanpham;

    public LaptopAdapter(Context context, ArrayList<Sanpham> mangSanpham) {
        this.context = context;
        this.mangSanpham = mangSanpham;
    }

    @Override
    public int getCount() {
        return mangSanpham.size();
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
        TextView txttenlaptop, txtgialaptop, txtmotalaptop;
        ImageView imglaptop;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_laptop_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.txttenlaptop = view.findViewById(R.id.textviewlaptop);
            viewHolder.txtgialaptop = view.findViewById(R.id.textviewgialaptop);
            viewHolder.txtmotalaptop = view.findViewById(R.id.textviewmotalaptop);
            viewHolder.imglaptop = view.findViewById(R.id.imageviewlaptop);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Sanpham sanpham = mangSanpham.get(i);
        viewHolder.txttenlaptop.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgialaptop.setText("Giá : " + decimalFormat.format(mangSanpham.get(i).getGiasanpham()) + " Đ");
        viewHolder.txtmotalaptop.setMaxLines(2);
        viewHolder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotalaptop.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham()).into(viewHolder.imglaptop);

        return view;
    }
}
