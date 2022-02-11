package com.sinhvien.lorenzo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    RecyclerView ctn;
    TextView txtDK;
    Button btnBack;
    ArrayList<CustomListViewItem> lstItem;
    ProductAdapter productAdapter;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ctn = findViewById(R.id.dataContianer);
        txtDK=findViewById(R.id.txtDK);
        btnBack=findViewById(R.id.btnBack);

        ListViewType();
        if(ctn.getAdapter().getItemCount()==0)
        {
            txtDK.setText("Không có kết quả cho: "+GetData());
        }
        else
        {
            txtDK.setText(txtDK.getText()+GetData());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private ArrayList<CustomListViewItem> LoadData(String query)
    {
        lstItem=new ArrayList<>();
        db=new DBHelper(this);
        ArrayList<Product>temp=new ArrayList<Product>();
        if(db.getProductsWithType(query).size()!=0)
        {
            temp=db.getProductsWithType(query);
        }
        else
        {
            temp=db.getProductsWithName(query);
        }

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
        productAdapter=new ProductAdapter(this,R.layout.custom_listview,LoadData(GetData()));

        LinearLayoutManager gridLayoutManager=new LinearLayoutManager(this);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ctn.setLayoutManager(gridLayoutManager);
        ctn.setAdapter(productAdapter);
    }
    private String GetData() {
        Intent i=getIntent();
        Bundle bundle=i.getBundleExtra("data");
        String id=bundle.getString("stringdata");
        return id;
    }
}