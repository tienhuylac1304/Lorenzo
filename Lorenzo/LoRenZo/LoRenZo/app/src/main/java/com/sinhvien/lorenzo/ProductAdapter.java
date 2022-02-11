package com.sinhvien.lorenzo;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    List<CustomListViewItem>list;
    Context context;
    int inflater;

    public ProductAdapter(Context context,int inflater,List<CustomListViewItem>listViewItems) {
        this.context=context;
        this.inflater=inflater;
        this.list=listViewItems;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(inflater,parent,false);
        return new ProductViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        CustomListViewItem product=this.list.get(position);
        if(product==null) {
            return;
        }
        Picasso.with(context).load(product.getImgBtn()).resize(500,400).centerCrop().into(holder.imgProduct);
        holder.txtName.setText(product.getTitle());
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(product.getPrice());
        holder.txtPrice.setText(formattedNumber+"VNĐ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,ChiTietSanPham.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id",product.getId());

                i.putExtra("data",bundle);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (this.list!=null)
            return list.size();
        return 0;
    }
    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName,txtPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imgProduct);
            txtName=itemView.findViewById(R.id.txtTitle);
            txtPrice=itemView.findViewById(R.id.txtPrice);
        }
    }
}
