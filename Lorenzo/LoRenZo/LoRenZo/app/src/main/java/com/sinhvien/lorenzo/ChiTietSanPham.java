package com.sinhvien.lorenzo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class ChiTietSanPham extends AppCompatActivity {

    Button btnThemGiohang,btnBack;
    Product product;
    DBHelper db;
    ImageView imgProduct;
    TextView txtName,txtID,txtPrice,txtCL,txtMoTa;
    int soLuong,giaTien;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        btnThemGiohang=findViewById(R.id.btnThem);
        btnBack=findViewById(R.id.btnBack);
        imgProduct=findViewById(R.id.imgView);
        txtCL=findViewById(R.id.txtCL);
        txtID=findViewById(R.id.txtid);
        txtMoTa=findViewById(R.id.txtMoTa);
        txtPrice=findViewById(R.id.txtGia);
        txtName=findViewById(R.id.txtname);

//        getDataFromAPI();
        GetID();
        BindingData();
        //LoadData1();

        btnThemGiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InitDialog();

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

//    public void getDataFromAPI()
//    {
//        String url="http://192.168.1.7:4000/product/";
//        String cL=txtCL.getText().toString();
//        String ma=txtID.getText().toString();
//
//
//        jsonArrayRequest=new JsonObjectRequest(Request.Method.GET,url+String.valueOf(GetID()),null,new Response.Listener<JSONObject>(){
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    JSONObject product=response.getJSONObject("product");
//                        Picasso.with(ChiTietSanPham.this).load(product.getString("image")).resize(800,800).centerCrop().into(imgProduct);
//                        txtName.setText(product.getString("name"));
//                        giaTien=product.getInt("price");
//                        NumberFormat formatter = new DecimalFormat("#,###");
//                        String formattedNumber = formatter.format(giaTien);
//                        txtPrice.setText(formattedNumber+"VNĐ");
//                        soLuong=product.getInt("numProduct");
//                        txtCL.setText(cL+String.valueOf(soLuong));
//                        txtMoTa.setText(product.getString("description"));
//                        txtID.setText(ma+String.valueOf(GetID()));
//
//
//                }
//
//                catch (JSONException e) {
//                    Toast.makeText(ChiTietSanPham.this,e.toString(),Toast.LENGTH_LONG).show();               }
//
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ChiTietSanPham.this, error.toString(), Toast.LENGTH_LONG).show();
//            }
//        });
//
//        mQueue= Volley.newRequestQueue(ChiTietSanPham.this);
//        mQueue.add(jsonArrayRequest);
//    }

    private void LoadData1()
    {
        API_Service.api.getProductWithId(GetID()).enqueue(new Callback<Product>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Product> call, retrofit2.Response<Product> response) {
                Toast.makeText(ChiTietSanPham.this, "SUCCED", Toast.LENGTH_SHORT).show();
                Product product=response.body();
                if(product!=null)
                {
                    String cL=txtCL.getText().toString();
                    String ma=txtID.getText().toString();

                    Picasso.with(ChiTietSanPham.this).load(product.getHinhAnh()).resize(800,800).centerCrop().into(imgProduct);
                    txtName.setText(product.getTenSP());
                    giaTien=product.getGia();
                    NumberFormat formatter = new DecimalFormat("#,###");
                    String formattedNumber = formatter.format(giaTien);
                    txtPrice.setText(formattedNumber+"VNĐ");
                    soLuong=product.getSoLuong();
                    txtCL.setText(cL+String.valueOf(soLuong));
                    txtMoTa.setText(product.getMoTa());
                    txtID.setText(ma+String.valueOf(GetID()));
                }

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

                Toast.makeText(ChiTietSanPham.this, "ERROR", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void BindingData() {

        db=new DBHelper(this);
        product=new Product();
        product=db.getProductsWithID(GetID());

        String cL=txtCL.getText().toString();
        String ma=txtID.getText().toString();

        Picasso.with(ChiTietSanPham.this).load(product.getHinhAnh()).resize(800,800).centerCrop().into(imgProduct);
        txtName.setText(product.getTenSP());
        giaTien=product.getGia();
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(giaTien);
        txtPrice.setText(formattedNumber+"VNĐ");
        soLuong=product.getSoLuong();
        txtCL.setText(cL+String.valueOf(soLuong));
        txtMoTa.setText(product.getMoTa());
        txtID.setText(ma+String.valueOf(GetID()));
    }

    private int GetID() {
        Intent i=getIntent();
        Bundle bundle=i.getBundleExtra("data");
        int id=bundle.getInt("id");
        return id;
    }

    private void InitDialog()
    {
        ImageButton imgBtnAdd,imgBtnRemove;
        TextView txtSL;
        Button btnAccept,btnCancel;

        BottomSheetDialog dialog=new BottomSheetDialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        imgBtnAdd=dialog.findViewById(R.id.btnTang);
        txtSL=dialog.findViewById(R.id.txtSL);
        imgBtnRemove=dialog.findViewById(R.id.btnGiam);
        btnAccept=dialog.findViewById(R.id.btnXacNhan);
        btnCancel=dialog.findViewById(R.id.btnHuy);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();

        imgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(txtSL.getText().toString())<soLuong)
                {
                    txtSL.setText(String.valueOf(Integer.parseInt(txtSL.getText().toString())+1));
                }
                else
                {
                    return;
                }
            }
        });

        imgBtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(txtSL.getText().toString())!=0)
                {
                    txtSL.setText(String.valueOf(Integer.parseInt(txtSL.getText().toString())-1));
                }
                else
                {
                    return;
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(txtSL.getText().toString())==0)
                {

                    dialog.cancel();
                }
                else
                {
                    int thanhTien=giaTien*Integer.parseInt(txtSL.getText().toString());
                    if(db.insertCart(GetID(),Integer.parseInt(txtSL.getText().toString()),thanhTien))
                    {
                        Toast.makeText(ChiTietSanPham.this,"Thêm vào giỏ hàng thành công",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(ChiTietSanPham.this, "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });

    }

}