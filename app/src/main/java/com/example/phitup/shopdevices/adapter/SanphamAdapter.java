package com.example.phitup.shopdevices.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phitup.shopdevices.R;
import com.example.phitup.shopdevices.activity.ChitietsanphamActivity;
import com.example.phitup.shopdevices.model.Sanpham;
import com.example.phitup.shopdevices.util.CheckConnection;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ViewHolder>{

    Context context;
    ArrayList<Sanpham> mangSanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> mangSanpham) {
        this.context = context;
        this.mangSanpham = mangSanpham;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.custom_sanphammoinhat_layout , null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.txttensanpham.setText(mangSanpham.get(i).getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiasanpham.setText("Giá : " + decimalFormat.format(mangSanpham.get(i).getGiasanpham()) + " Đ");
        Picasso.with(context).load(mangSanpham.get(i).getHinhanhsanpham()).into(viewHolder.imghinhsanpham);
    }

    @Override
    public int getItemCount() {
        return mangSanpham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imghinhsanpham;
        TextView txttensanpham , txtgiasanpham;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imghinhsanpham = itemView.findViewById(R.id.imageviewsanpham);
            txtgiasanpham = itemView.findViewById(R.id.textviewgiasanpham);
            txttensanpham = itemView.findViewById(R.id.textviewtensanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChitietsanphamActivity.class);
                    intent.putExtra("chitietsanpham", mangSanpham.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
