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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    List<CustomListViewItemCart>list;
    Context context;
    int inflater;

    public CartAdapter(Context context, int inflater, List<CustomListViewItemCart>listViewItems) {
        this.context=context;
        this.inflater=inflater;
        this.list=listViewItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(inflater,parent,false);
        return new CartViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CustomListViewItemCart product=this.list.get(position);
        if(product==null) {
            return;
        }
        Picasso.with(context).load(product.getImgBtn()).resize(500,400).centerCrop().into(holder.imgProduct);
        holder.txtName.setText(product.getTitle());
        holder.txtSL.setText(String.valueOf(product.getSoLuong()));
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(product.getPrice());
        holder.txtPrice.setText(formattedNumber+"VNĐ");
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db=new DBHelper(context);
                db.deleteCart(product.getId());
                list.remove(holder.getAdapterPosition());
                notifyItemRemoved(position);
                CartFragment.TinhTongTien();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (this.list!=null)
            return list.size();
        return 0;
    }
    public class CartViewHolder extends RecyclerView.ViewHolder {
        SwipeRevealLayout container;
        ImageView imgProduct;
        TextView txtName,txtPrice,txtSL;
        ImageButton btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            container=itemView.findViewById(R.id.cartItemLayout);
            imgProduct=itemView.findViewById(R.id.imgProduct);
            txtName=itemView.findViewById(R.id.txtTitle);
            txtPrice=itemView.findViewById(R.id.txtPrice);
            txtSL=itemView.findViewById(R.id.txtSoLuong);
            btnDelete=itemView.findViewById(R.id.btnDelete);

        }
    }
}
