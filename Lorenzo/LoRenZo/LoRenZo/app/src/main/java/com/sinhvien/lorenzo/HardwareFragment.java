package com.sinhvien.lorenzo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HardwareFragment extends Fragment {
    RecyclerView ctn;
    ArrayList<CustomListViewItem> lstItem;
    ProductAdapter productAdapter;
    DBHelper db;
    JsonObjectRequest jsonArrayRequest;
    RequestQueue mQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.electron_fragment, container, false);
        ctn = v.findViewById(R.id.container);
//        getDataFromAPI();
        ListViewType();
        return v;
    }
    public void getDataFromAPI()
    {
        lstItem=new ArrayList<CustomListViewItem>();
        String url="http://192.168.1.7:4000/product/type/2";
        jsonArrayRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray products=response.getJSONArray("product");
                    for(int i=0;i<products.length();i++)
                    {
                        JSONObject jsonObject= products.getJSONObject(i);
                        CustomListViewItem customListViewItem=new CustomListViewItem();
                        customListViewItem.setId(jsonObject.getInt("id"));
                        customListViewItem.setTitle(jsonObject.getString("name"));
                        customListViewItem.setPrice(jsonObject.getInt("price"));
                        customListViewItem.setImgBtn(jsonObject.getString("image"));
                        lstItem.add(customListViewItem);
                    }
                    //ListViewType(lstItem);

                }

                catch (JSONException e) {
                    Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();               }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        mQueue= Volley.newRequestQueue(getActivity());
        mQueue.add(jsonArrayRequest);
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
        productAdapter=new ProductAdapter(getActivity(),R.layout.custom_listview,LoadData("Linh kiện điện tử"));

        LinearLayoutManager gridLayoutManager=new LinearLayoutManager(getActivity());
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ctn.setLayoutManager(gridLayoutManager);
        ctn.setAdapter(productAdapter);
    }
}
