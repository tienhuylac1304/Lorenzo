package com.sinhvien.lorenzo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllFragment extends Fragment {
    RecyclerView ctn;
    ArrayList<CustomListViewItem> lstItem;
    ProductAdapter productAdapter;
    DBHelper db;
//    JsonObjectRequest jsonArrayRequest;
//    RequestQueue mQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all_fragment, container, false);
        ctn = v.findViewById(R.id.container);
//        getDataFromAPI();
 //       LoadData1();
        GridViewtype();
        return v;
    }
    private ArrayList<CustomListViewItem> LoadData()
    {
        lstItem=new ArrayList<CustomListViewItem>();
        db=new DBHelper(getActivity());
        ArrayList<Product>temp=new ArrayList<Product>();
        temp=db.getAllProduct();
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
    //http://192.168.1.7:4000/product
//    public void getDataFromAPI()
//    {
//        lstItem=new ArrayList<CustomListViewItem>();
//        String url="http://192.168.1.7:4000/product";
//        jsonArrayRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){
//           @Override
//           public void onResponse(JSONObject response) {
//
//               try {
//                   JSONArray products=response.getJSONArray("product");
//                   for(int i=0;i<products.length();i++)
//                   {
//                       JSONObject jsonObject= products.getJSONObject(i);
//                       CustomListViewItem customListViewItem=new CustomListViewItem();
//                       customListViewItem.setId(jsonObject.getInt("id"));
//                       customListViewItem.setTitle(jsonObject.getString("name"));
//                       customListViewItem.setPrice(jsonObject.getInt("price"));
//                       customListViewItem.setImgBtn(jsonObject.getString("image"));
//                       lstItem.add(customListViewItem);
//                   }
//                   GridViewtype(lstItem);
//
//               }
//
//               catch (JSONException e) {
//                   Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();               }
//
//           }
//
//       }, new Response.ErrorListener() {
//           @Override
//           public void onErrorResponse(VolleyError error) {
//               Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
//           }
//       });
//
//        mQueue=Volley.newRequestQueue(getActivity());
//        mQueue.add(jsonArrayRequest);
//    }
    private void GridViewtype()
    {
        productAdapter=new ProductAdapter(getActivity(),R.layout.custom_gridview,LoadData());

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        ctn.setLayoutManager(gridLayoutManager);
        ctn.setAdapter(productAdapter);
    }
    private void LoadData1()
    {
        API_Service.api.getAllProduct().enqueue(new Callback<ListProducts>() {
            @Override
            public void onResponse(Call<ListProducts> call, Response<ListProducts> response) {
                Toast.makeText(getActivity(), "SUCCED", Toast.LENGTH_SHORT).show();
                lstItem=new ArrayList<CustomListViewItem>();
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
                GridViewtype();

            }

            @Override
            public void onFailure(Call<ListProducts> call, Throwable t) {

                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();

            }
        });
    }
}