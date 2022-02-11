package com.sinhvien.lorenzo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class ToyFragment extends Fragment {
    RecyclerView ctn;
    ArrayList<CustomListViewItem> lstItem;
    ProductAdapter productAdapter;
    DBHelper db;
    JsonObjectRequest jsonArrayRequest;
    RequestQueue mQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.toy_fragment, container, false);
        ctn = v.findViewById(R.id.container);

        ListViewType();
        //LoadData1();
        //getDataFromAPI();

        return v;
    }

    private void LoadData1()
    {
        lstItem=new ArrayList<>();
        API_Service.api.getProductWithType(3).enqueue(new Callback<ListProducts>() {
            @Override
            public void onResponse(Call<ListProducts> call, retrofit2.Response<ListProducts> response) {
                Toast.makeText(getActivity(), "SUCCED", Toast.LENGTH_SHORT).show();
                ListProducts listP=response.body();
                if(listP!=null)
                {
                    for (int i=0;i<listP.product.size();i++)
                    {
                        CustomListViewItem item=new CustomListViewItem();
                        item.setId(listP.product.get(i).getIdSP());
                        item.setTitle(listP.product.get(i).getTenSP());
                        item.setImgBtn(listP.product.get(i).getHinhAnh());
                        item.setPrice(listP.product.get(i).getGia());
                        lstItem.add(item);
                    }
                }
                //ListViewType(lstItem);
            }

            @Override
            public void onFailure(Call<ListProducts> call, Throwable t) {

                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private ArrayList<CustomListViewItem> LoadData(String type)
    {
        lstItem=new ArrayList<CustomListViewItem>();
        db=new DBHelper(getActivity());
        ArrayList<Product>temp=new ArrayList<Product>();
        temp=db.getProductsWithType(type);
        for(int i=0;i<temp.size();i++)
        {

            CustomListViewItem data=new CustomListViewItem();
            data.setTitle(temp.get(i).getTenSP());
            data.setImgBtn(temp.get(i).getHinhAnh());
            data.setPrice(temp.get(i).getGia());
            data.setId(temp.get(i).getIdSP());
            lstItem.add(data);

        }
        return lstItem;
    }
    private void ListViewType()
    {
        productAdapter=new ProductAdapter(getActivity(),R.layout.custom_listview,LoadData("Đồ chơi"));

        LinearLayoutManager gridLayoutManager=new LinearLayoutManager(getActivity());
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ctn.setLayoutManager(gridLayoutManager);
        ctn.setAdapter(productAdapter);
    }
}
