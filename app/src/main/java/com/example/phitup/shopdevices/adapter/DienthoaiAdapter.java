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

public class DienthoaiAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sanpham> mangSanpham;

    public DienthoaiAdapter(Context context, ArrayList<Sanpham> mangSanpham) {
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
        TextView txttendienthoai, txtgiadienthoai, txtmotadienthoai;
        ImageView imgdienthoai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_dienthoai_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.txttendienthoai = view.findViewById(R.id.textviewdienthoai);
            viewHolder.txtgiadienthoai = view.findViewById(R.id.textviewgiadienthoai);
            viewHolder.txtmotadienthoai = view.findViewById(R.id.textviewmotadienthoai);
            viewHolder.imgdienthoai = view.findViewById(R.id.imageviewdienthoai);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Sanpham sanpham = mangSanpham.get(i);
        viewHolder.txttendienthoai.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadienthoai.setText("Giá : " + decimalFormat.format(mangSanpham.get(i).getGiasanpham()) + " Đ");
        viewHolder.txtmotadienthoai.setMaxLines(2);
        viewHolder.txtmotadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotadienthoai.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham()).into(viewHolder.imgdienthoai);

        return view;
    }
}
