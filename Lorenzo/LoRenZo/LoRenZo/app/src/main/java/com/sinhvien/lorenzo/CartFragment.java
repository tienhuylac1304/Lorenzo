package com.sinhvien.lorenzo;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView ctn;
    static TextView txtTongTien;
    Button btnMua;
    static ArrayList<CustomListViewItemCart> lstItem;
    CartAdapter cartAdapter;
    DBHelper db;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        ctn = v.findViewById(R.id.container);
        btnMua=v.findViewById(R.id.btnMua);
        txtTongTien=v.findViewById(R.id.txtTotal);
        db=new DBHelper(getActivity());
        LoadData();
        ListViewType();

        return v;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void LoadData()
    {
        lstItem=new ArrayList<>();
        ArrayList<Cart>temp=new ArrayList<Cart>();
        temp=db.getAllCart();
        for(int i=0;i<temp.size();i++)
        {

            CustomListViewItemCart data=new CustomListViewItemCart();
            data.setTitle(temp.get(i).getTenSP());
            data.setImgBtn(temp.get(i).getHinhAnh());
            data.setSoLuong(temp.get(i).getSoLuong());
            data.setPrice(temp.get(i).getThanhTien());
            data.setId(temp.get(i).getId());
            lstItem.add(data);
        }
        TinhTongTien();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void TinhTongTien() {
        int tongTien=0;
        for(int i=0;i<CartFragment.lstItem.size();i++)
        {
            tongTien=tongTien+lstItem.get(i).getPrice();
        }
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(tongTien);
        txtTongTien.setText(formattedNumber+"VNĐ");
    }

    private void ListViewType()
    {
        cartAdapter=new CartAdapter(getActivity(),R.layout.custom_listview_cart,lstItem);

        LinearLayoutManager gridLayoutManager=new LinearLayoutManager(getActivity());
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ctn.setLayoutManager(gridLayoutManager);
        ctn.setAdapter(cartAdapter);

    }
    //chưa realtime tổng tiền
}
